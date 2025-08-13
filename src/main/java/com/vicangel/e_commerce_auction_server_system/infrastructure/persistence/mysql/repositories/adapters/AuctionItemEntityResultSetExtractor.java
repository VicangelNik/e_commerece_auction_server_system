package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.adapters;

import java.sql.ResultSet;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionItemEntity;

@Component
public class AuctionItemEntityResultSetExtractor implements ResultSetExtractor<AuctionItemEntity> {

  @Override
  public AuctionItemEntity extractData(ResultSet rs) throws DataAccessException {
    return null;
    //        final Long auctionItemID = rs.getObject("ai.id", Long.class);
//        if (auctionItemID != null) {
//          if (auctionEntity.auctionItems().stream()
//            .map(AuctionItemEntity::id).noneMatch(x -> x.longValue() == auctionItemID)) { // new item entry
//            AuctionItemEntity auctionItemEntity = AuctionItemEntity.builder()
//              .id(auctionItemID)
//              .auctionId(auctionID)
//              .name(rs.getString("ai.name"))
//              .description(rs.getString("ai.description"))
//              .location(rs.getString("ai.location"))
//              .latitude(rs.getDouble("ai.latitude"))
//              .latitude(rs.getDouble("ai.longitude"))
//              .country(rs.getString("ai.country"))
//              .build();
//
//            auctionEntity.auctionItems().add(auctionItemEntity);
//          }
//        }
  }
}
