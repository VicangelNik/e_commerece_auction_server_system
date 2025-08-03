package com.vicangel.e_commerce_auction_server_system.core.api;

import com.vicangel.e_commerce_auction_server_system.core.model.Auction;

public interface AuctionService {

  int saveAuction(Auction auction);

  void beginAuction(long auctionId);
}
