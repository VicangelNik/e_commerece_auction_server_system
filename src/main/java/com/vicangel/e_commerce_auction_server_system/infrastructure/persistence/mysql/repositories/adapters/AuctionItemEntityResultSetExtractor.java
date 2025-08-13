package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionItemEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.ItemCategoryEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.ItemImageEntity;

@Component
public class AuctionItemEntityResultSetExtractor implements ResultSetExtractor<AuctionItemEntity> {

  @Override
  public AuctionItemEntity extractData(ResultSet rs) throws DataAccessException, SQLException {

    Map<Long, AuctionItemEntity> auctionItemEntityMap = new HashMap<>();

    while (rs.next()) {
      final Long auctionItemEntityID = rs.getLong("ai.id");

      if (auctionItemEntityMap.containsKey(auctionItemEntityID)) { // auction item already exists
        final AuctionItemEntity auctionItemEntity = auctionItemEntityMap.get(auctionItemEntityID);
        addCategoryToItem(rs, auctionItemEntity);
      } else { // new auction item entry

        final Long imageID = rs.getObject("ii.item_id", Long.class);
        ItemImageEntity image = null;

        if (imageID != null) {
          image = new ItemImageEntity(
            imageID,
            rs.getString("ii.name"),
            rs.getString("ii.description"),
            rs.getString("ii.type"),
            rs.getBytes("ii.image")
          );
        }

        final var entity = AuctionItemEntity.builder()
          .id(auctionItemEntityID)
          .auctionId(rs.getLong("ai.auction_id"))
          .name(rs.getString("ai.name"))
          .description(rs.getString("ai.description"))
          .location(rs.getString("ai.location"))
          .latitude(rs.getDouble("ai.latitude"))
          .latitude(rs.getDouble("ai.longitude"))
          .country(rs.getString("ai.country"))
          .categories(new HashSet<>())
          .image(image)
          .build();

        addCategoryToItem(rs, entity);

        auctionItemEntityMap.put(auctionItemEntityID, entity);
      }
    }
    return auctionItemEntityMap.values().stream().findFirst().orElse(null);
  }

  private void addCategoryToItem(ResultSet rs, AuctionItemEntity entity) throws SQLException {
    final Long categoryID = rs.getObject("c.id", Long.class); // might be null
    if (categoryID != null) {
      final var category = new ItemCategoryEntity(
        categoryID,
        rs.getString("c.name"),
        rs.getString("c.description")
      );
      entity.categories().add(category);
    }
  }
}
