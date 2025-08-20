package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories;

import java.util.List;
import java.util.Optional;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionItemEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.BidEntity;

public interface AuctionRepository {

  /**
   * @return auction ID
   */
  long save(AuctionEntity entity);

  int beginAuction(long auctionId);

  /**
   * @return auction item ID
   */
  long insertAuctionItem(AuctionItemEntity entity);

  Optional<AuctionEntity> findById(long id, boolean fetchItems);

  List<AuctionEntity> findAll(boolean fetchItems);

  /**
   * @return Bid ID
   */
  long saveBid(BidEntity entity);

  int deleteById(long id);
}
