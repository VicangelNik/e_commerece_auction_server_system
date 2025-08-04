package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories;

import java.util.List;
import java.util.Optional;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionItemEntity;

public interface ActionRepository {

  /**
   * @return auction ID
   */
  long save(AuctionEntity entity);

  void beginAuction(long auctionId);

  /**
   * @return auction item ID
   */
  long insertAuctionItem(AuctionItemEntity entity);

  Optional<AuctionEntity> findById(long id);

  List<AuctionEntity> findAll();
}
