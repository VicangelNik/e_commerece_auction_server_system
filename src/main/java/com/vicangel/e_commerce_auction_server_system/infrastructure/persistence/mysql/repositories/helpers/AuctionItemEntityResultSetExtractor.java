package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionItemEntity;

@Component
public class AuctionItemEntityResultSetExtractor implements ResultSetExtractor<List<AuctionItemEntity>> {

  @Override
  public List<AuctionItemEntity> extractData(final ResultSet rs) throws SQLException {

    Map<Long, AuctionItemEntity> auctionItemEntityMap = new HashMap<>();

    while (rs.next()) {
      final Long itemID = rs.getLong("ai.id");

      if (auctionItemEntityMap.containsKey(itemID)) { // auction item already exists
        final AuctionItemEntity entity = auctionItemEntityMap.get(itemID);
        addCategoryToItem(rs, entity);
        auctionItemEntityMap.put(itemID, entity);
      } else { // new auction item entry

        final var entity = AuctionItemEntity.builder()
          .id(itemID)
          .name(rs.getString("ai.name"))
          .description(rs.getString("ai.description"))
          .location(rs.getString("ai.location"))
          .latitude(rs.getDouble("ai.latitude"))
          .latitude(rs.getDouble("ai.longitude"))
          .country(rs.getString("ai.country"))
          .categories(new HashSet<>())
          .image(rs.getString("ai.image"))
          .imageContentType(rs.getString("ai.image_content_type"))
          .build();

        addCategoryToItem(rs, entity);

        auctionItemEntityMap.put(itemID, entity);
      }
    }
    return auctionItemEntityMap.values().stream().toList();
  }

  private void addCategoryToItem(@NonNull final ResultSet rs,
                                 @NonNull final AuctionItemEntity entity) throws SQLException {
    final Long categoryID = rs.getObject("ct.id", Long.class); // might be null
    if (categoryID != null) {
      entity.categories().add(categoryID);
    }
  }
}
