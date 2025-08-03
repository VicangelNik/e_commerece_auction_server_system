package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionEntity;

public interface ActionRepository {

  int save(AuctionEntity entity);

  void beginAuction(long auctionId);
}
