package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.BidEntity;

import static com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.helpers.RepoUtils.toInstant;

@Component
public final class AuctionEntityResultSetExtractor implements ResultSetExtractor<List<AuctionEntity>> {

  @Override
  public List<AuctionEntity> extractData(ResultSet rs) throws SQLException {
    Map<Long, AuctionEntity> auctionEntityMap = new HashMap<>();

    while (rs.next()) {
      final Long auctionID = rs.getLong("ac.id");

      if (auctionEntityMap.containsKey(auctionID)) { // auction already exists

        final AuctionEntity entity = auctionEntityMap.get(auctionID);

        addBidToAuction(rs, entity);
      } else { // new auction entry
        final var entity = AuctionEntity.builder()
          .id(auctionID)
          .title(rs.getString("ac.title"))
          .created(toInstant(rs, "ac.created"))
          .started(toInstant(rs, "ac.started"))
          .endDate(toInstant(rs, "ac.end_date"))
          .firstBid(rs.getFloat("ac.first_bid"))
          .currentBestBid(rs.getFloat("ac.current_best_bid"))
          .sellerId(rs.getLong("ac.seller_id"))
          .auctionItems(new HashSet<>())
          .bids(new HashSet<>())
          .build();

        addBidToAuction(rs, entity);

        auctionEntityMap.put(auctionID, entity);
      }
    }
    return new ArrayList<>(auctionEntityMap.values());
  }

  private void addBidToAuction(@NonNull final ResultSet rs, @NonNull final AuctionEntity entity) throws SQLException {
    final Long bidID = rs.getObject("b.id", Long.class); // might be null
    if (bidID != null) {
      final var bidEntity = new BidEntity(
        bidID,
        entity.id(),
        rs.getLong("b.bidder_id"),
        toInstant(rs, "time_submitted"),
        rs.getDouble("b.amount")
      );
      entity.bids().add(bidEntity);
    }
  }
}
