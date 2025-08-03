package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.api;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request.SaveAuctionRequest;

public interface AuctionOpenAPI {

  ResponseEntity<Void> createAuction(SaveAuctionRequest request);

  ResponseEntity<Void> beginAuction(long auctionId);
}
