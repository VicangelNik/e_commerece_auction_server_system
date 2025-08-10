package com.vicangel.e_commerce_auction_server_system.endpoint.rest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.AuctionItemDTO;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveAuctionRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveBidRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.AuctionResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.IdResponse;

public interface AuctionOpenAPI {

  ResponseEntity<IdResponse> createAuction(SaveAuctionRequest request);

  ResponseEntity<Void> beginAuction(long auctionId);

  ResponseEntity<IdResponse> addAuctionItem(AuctionItemDTO request);

  ResponseEntity<AuctionResponse> findById(long id);

  ResponseEntity<List<AuctionResponse>> findAll();

  ResponseEntity<IdResponse> addBid(SaveBidRequest request);
}
