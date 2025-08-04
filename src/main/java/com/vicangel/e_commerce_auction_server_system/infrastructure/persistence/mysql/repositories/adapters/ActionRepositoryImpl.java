package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.adapters;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.e_commerce_auction_server_system.core.model.commons.ErrorCodes;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionItemEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.ActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ActionRepositoryImpl implements ActionRepository {

  private static final String insertAuctionSQL = "INSERT INTO auctions (created, ends, first_bid, number_of_bids, seller_id) VALUES (?,?,?,?,?)";
  private static final String insertAuctionItemSQL = "INSERT INTO auction_items (auction_id, name, description, location, latitude, longitude, country) VALUES (?,?,?,?,?,?,?)";
  private static final String insertItemCategorySQL = "INSERT INTO item_categories(auction_item_id, category_id) VALUES (?,?)";
  private static final String insertImageToItemSQL = "INSERT INTO item_image (item_id, name, description, type, image) VALUES (?,?,?,?,?)";
  private static final String findAllSQL = """
       SELECT
           ac.id, ac.created, ac.started, ac.ends, ac.first_bid, ac.currently, ac.number_of_bids, ac.seller_id,
           ai.id, ai.name, ai.description, ai.location, ai.latitude, ai.longitude, ai.country,
           c.id, c.name,
           ii.name, ii.description, ii.type, ii.image,
           b.id, b.bidder_id, b.time, b.amount
       FROM `auction-db`.auctions ac
                INNER JOIN `auction-db`.auction_items ai on ac.id = ai.auction_id
                INNER JOIN `auction-db`.item_categories ic ON ic.auction_item_id = ai.id
                INNER JOIN `auction-db`.categories c ON c.id = ic.category_id
                INNER JOIN `auction-db`.item_image ii ON ii.item_id = ai.id
                INNER JOIN `auction-db`.bids b on ac.id = b.auction_id
    """;

  private static final String findByIdClauseSQL = " WHERE ac.id = ?";

  private final JdbcTemplate jdbcTemplate;

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
  public void beginAuction(final long auctionId) {
    this.jdbcTemplate.update("UPDATE auctions set started = ? where id = ?", Instant.now(), auctionId);
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
  public Optional<AuctionEntity> findById(long id) {
    try {
      AuctionEntity entity = jdbcTemplate.queryForObject(
        findAllSQL + findByIdClauseSQL,
        new BeanPropertyRowMapper<>(AuctionEntity.class),
        id
      );
      return Optional.ofNullable(entity);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<AuctionEntity> findAll() {
    return jdbcTemplate.queryForList(findAllSQL, AuctionEntity.class);
  }
}
