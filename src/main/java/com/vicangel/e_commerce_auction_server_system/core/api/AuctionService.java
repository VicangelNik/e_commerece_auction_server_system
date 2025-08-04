package com.vicangel.e_commerce_auction_server_system.core.api;

import java.util.List;
import java.util.Optional;

import com.vicangel.e_commerce_auction_server_system.core.model.Auction;
import com.vicangel.e_commerce_auction_server_system.core.model.AuctionItem;

public interface AuctionService {

  /**
   * @return auction ID
   */
  long saveAuction(Auction auction);

  void beginAuction(long auctionId);

  /**
   * @return auction item's ID
   */
  long addAuctionItem(AuctionItem auctionItem);

  Optional<Auction> findById(Long id);

  List<Auction> findAll();
}
