package com.vicangel.e_commerce_auction_server_system.core.api;

import java.util.List;
import java.util.Optional;

import com.vicangel.e_commerce_auction_server_system.core.model.Auction;
import com.vicangel.e_commerce_auction_server_system.core.model.AuctionItem;
import com.vicangel.e_commerce_auction_server_system.core.model.Bid;

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

  Optional<Auction> findById(long id, boolean fetchItems);

  List<Auction> findAll(boolean fetchItems);

  long bid(Bid bid);

  boolean deleteAuction(long id);
}
