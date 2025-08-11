package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicangel.e_commerce_auction_server_system.core.api.AuctionService;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.api.AuctionOpenAPI;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.AuctionItemDTO;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveAuctionRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveBidRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.AuctionResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.IdResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers.AuctionEndpointMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RequestMapping("/auction")
@RestController
@Slf4j
final class AuctionController implements AuctionOpenAPI {

  private final AuctionEndpointMapper mapper;
  private final AuctionService service;

  @PostMapping(value = "/create", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<IdResponse> createAuction(@RequestBody final SaveAuctionRequest request) {

    final long result = service.saveAuction(mapper.mapRequestToModel(request));

    return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(result));
  }

  @PatchMapping(value = "/{id}/begin")
  @Override
  public ResponseEntity<Void> beginAuction(@PathVariable("id") final long auctionId) {

    service.beginAuction(auctionId);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  @PostMapping(value = "/item/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<IdResponse> addAuctionItem(@RequestBody final AuctionItemDTO request) {

    final long result = service.addAuctionItem(mapper.mapAuctionRequestToModel(request));

    return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(result));
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<AuctionResponse> findById(@PathVariable final long id) {
    return service.findById(id)
      .map(mapper::mapModelToResponse)
      .map(r -> ResponseEntity.status(HttpStatus.OK).body(r))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<AuctionResponse>> findAll() {

    List<AuctionResponse> response = service.findAll().stream().map(mapper::mapModelToResponse).toList();

    if (response.isEmpty()) return ResponseEntity.notFound().build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping(value = "/bid/add", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<IdResponse> addBid(@RequestBody final SaveBidRequest request) {

    final long result = service.bid(mapper.mapBidRequestToModel(request));

    return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(result));
  }

//
//  @GetMapping(value = "/{id}/categories", produces = APPLICATION_JSON_VALUE)
//  @Override
//  private void getAuctionsByCategories() {
//
//  }
}
