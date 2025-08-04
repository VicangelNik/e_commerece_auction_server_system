package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.AuctionItemDTO;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request.SaveAuctionRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.response.AuctionResponse;

public interface AuctionOpenAPI {

  ResponseEntity<Long> createAuction(SaveAuctionRequest request);

  ResponseEntity<Void> beginAuction(long auctionId);

  ResponseEntity<Long> addAuctionItem(AuctionItemDTO request);

  ResponseEntity<AuctionResponse> findById(long id);

  ResponseEntity<List<AuctionResponse>> findAll();
}
