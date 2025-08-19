package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.adapters;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.e_commerce_auction_server_system.core.model.commons.ErrorCodes;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionItemEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.BidEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.AuctionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class AuctionRepositoryImpl implements AuctionRepository {

  private static final String insertAuctionSQL = "INSERT INTO auctions (created, ends, first_bid, number_of_bids, seller_id) VALUES (?,?,?,?,?)";
  private static final String insertAuctionItemSQL = "INSERT INTO auction_items (auction_id, name, description, location, latitude, longitude, country) VALUES (?,?,?,?,?,?,?)";
  private static final String insertItemCategorySQL = "INSERT INTO item_categories(auction_item_id, category_id) VALUES (?,?)";
  private static final String insertImageToItemSQL = "INSERT INTO item_image (item_id, name, description, type, image) VALUES (?,?,?,?,?)";
  private static final String findAllSQL = """
       SELECT
           ac.id, ac.created, ac.started, ac.ends, ac.first_bid, ac.currently, ac.number_of_bids, ac.seller_id,
           b.id, b.bidder_id, b.time_submitted, b.amount
       FROM `auction-db`.auctions ac
                LEFT JOIN `auction-db`.bids b on ac.id = b.auction_id
    """;
  private static final String findItemByIdSQL = """
       SELECT
           ai.id, ai.name, ai.description, ai.location, ai.latitude, ai.longitude, ai.country,
           ct.id, ct.name, ct.description,
           im.name, im.description, im.type, im.image
       FROM `auction-db`.auction_items ai
           LEFT JOIN `auction-db`.item_image im ON im.item_id = ai.id
           LEFT JOIN `auction-db`.item_categories ic ON ic.auction_item_id = ai.id
           LEFT JOIN `auction-db`.categories ct ON ct.id = ic.category_id
       WHERE ai.auction_id = ?
    """;

  private static final String findByIdClauseSQL = " WHERE ac.id = ?";
  private static final String insertBidSQL = "INSERT INTO bids (auction_id, bidder_id, time_submitted, amount) VALUES (?,?,?,?)";
  private final JdbcTemplate jdbcTemplate;
  private final AuctionEntityResultSetExtractor extractor;
  private final AuctionItemEntityResultSetExtractor extractorItem;

  public long save(final @NonNull AuctionEntity entity) {

    // https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/jdbc.html#jdbc-auto-genereted-keys

    final var keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(insertAuctionSQL, Statement.RETURN_GENERATED_KEYS);
      ps.setTimestamp(1, Timestamp.from(entity.created()));
      ps.setTimestamp(2, Timestamp.from(entity.ends()));
      ps.setFloat(3, entity.firstBid());
      ps.setInt(4, entity.numberOfBids());
      ps.setLong(5, entity.sellerId());
      return ps;
    }, keyHolder);

    if (keyHolder.getKey() == null) return ErrorCodes.SQL_ERROR.getCode();

    return keyHolder.getKey().longValue();
  }

  @Override
  public int beginAuction(final long auctionId) {
    return this.jdbcTemplate.update("UPDATE auctions set started = ? where id = ?", Instant.now(), auctionId);
  }

  @Override
  public long insertAuctionItem(@NonNull final AuctionItemEntity entity) {

    final var keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(insertAuctionItemSQL, Statement.RETURN_GENERATED_KEYS);
      ps.setLong(1, entity.auctionId());
      ps.setString(2, entity.name());
      ps.setString(3, entity.description());
      ps.setString(4, entity.location());
      ps.setDouble(5, entity.latitude());
      ps.setDouble(6, entity.longitude());
      ps.setString(7, entity.country());
      return ps;
    }, keyHolder);

    if (keyHolder.getKey() == null) return ErrorCodes.SQL_ERROR.getCode();

    final long auctionItemID = keyHolder.getKey().longValue();

    entity.categories()
      .forEach(entityCategoryId -> {

        int categoryUpdateResult = jdbcTemplate.update(insertItemCategorySQL, auctionItemID, entityCategoryId);

        if (categoryUpdateResult != 1) log.error("Category insert failed: {}", categoryUpdateResult);
      });

    if (entity.image() != null) {
      int imageUpdateResult = jdbcTemplate.update(insertImageToItemSQL,
                                                  auctionItemID,
                                                  entity.image().name(),
                                                  entity.image().description(),
                                                  entity.image().type(),
                                                  entity.image().image());

      if (imageUpdateResult != 1) log.error("Image insert failed: {}", imageUpdateResult);
    }

    return auctionItemID;
  }

  @Override
  public Optional<AuctionEntity> findById(final long id, final boolean fetchItems) {
    try {
      final List<AuctionEntity> l = jdbcTemplate.query(findAllSQL + findByIdClauseSQL, extractor, id);

      if (l == null || l.isEmpty()) return Optional.empty();

      final AuctionEntity entity = l.getFirst();

      if (fetchItems) {
        final List<AuctionItemEntity> items = jdbcTemplate.query(findItemByIdSQL, extractorItem, id);
        if (items != null && !items.isEmpty()) {
          entity.auctionItems().addAll(items);
        }
      }
      return Optional.of(entity);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<AuctionEntity> findAll(final boolean fetchItems) {
    List<AuctionEntity> l = jdbcTemplate.query(findAllSQL, extractor);

    if (l == null) return Collections.emptyList();

    if (fetchItems) {
      l.forEach(entity -> {
        final List<AuctionItemEntity> items = jdbcTemplate.query(findItemByIdSQL, extractorItem, entity.id());
        if (items != null && !items.isEmpty()) {
          entity.auctionItems().addAll(items);
        }
      });
    }
    return l;
  }

  @Override
  public long saveBid(@NonNull final BidEntity entity) {

    final var keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(insertBidSQL, Statement.RETURN_GENERATED_KEYS);
      ps.setLong(1, entity.auctionId());
      ps.setLong(2, entity.bidderId());
      ps.setTimestamp(3, Timestamp.from(entity.bidSubmittedTime()));
      ps.setDouble(4, entity.amount());
      return ps;
    }, keyHolder);

    if (keyHolder.getKey() == null) return ErrorCodes.SQL_ERROR.getCode();

    return keyHolder.getKey().longValue();
  }
}
