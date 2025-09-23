package com.vicangel.e_commerce_auction_server_system.endpoint.rest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.AuctionItemRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveAuctionRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveBidRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.AuctionResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.IdResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.validation.ValidImageFile;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

public interface AuctionOpenAPI {

  ResponseEntity<IdResponse> createAuction(@Valid SaveAuctionRequest request);

  ResponseEntity<AuctionResponse> beginAuction(@Valid @Positive long auctionId);

  ResponseEntity<IdResponse> addAuctionItem(@Valid AuctionItemRequest request,
                                            @ValidImageFile MultipartFile image);

  ResponseEntity<AuctionResponse> findById(@Valid @Positive long id, boolean fetchItems);

  ResponseEntity<List<AuctionResponse>> findAll(boolean fetchItems);

  ResponseEntity<AuctionResponse> addBid(@Valid SaveBidRequest request);

  ResponseEntity<Void> removeAuction(@Valid @Positive long id);

  ResponseEntity<List<AuctionResponse>> findByCategory(@Valid @Positive long categoryId, boolean fetchItems);
}
