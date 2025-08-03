package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.adapters;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.ActionRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ActionRepositoryImpl implements ActionRepository {

  private final JdbcTemplate jdbcTemplate;

  public int save(final @NonNull AuctionEntity entity) {

    // https://docs.spring.io/spring-framework/docs/3.0.x/spring-framework-reference/html/jdbc.html#jdbc-auto-genereted-keys

    final String sql = "INSERT INTO auctions (created, ends, first_bid, number_of_bids, seller_id) VALUES (?,?,?,?,?);";
    final var keyHolder = new GeneratedKeyHolder();

    return jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      ps.setTimestamp(1, Timestamp.from(entity.created()));
      ps.setTimestamp(2, Timestamp.from(entity.ends()));
      ps.setFloat(3, entity.firstBid());
      ps.setInt(4, entity.numberOfBids());
      ps.setLong(5, entity.sellerId());
      return ps;
    }, keyHolder);

//    return jdbcTemplate.update(sql,
//                               entity.created(),
//                               entity.ends(),
//                               entity.firstBid(),
//                               entity.numberOfBids(),
//                               entity.sellerId());
  }

  @Override
  public void beginAuction(final long auctionId) {
    this.jdbcTemplate.update("UPDATE auctions set started = ? where id = ?", Instant.now(), auctionId);
  }
}
