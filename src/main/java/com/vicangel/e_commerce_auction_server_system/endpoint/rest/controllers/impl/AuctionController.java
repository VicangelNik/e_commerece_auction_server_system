package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicangel.e_commerce_auction_server_system.core.api.AuctionService;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.api.AuctionOpenAPI;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request.SaveAuctionRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.mappers.AuctionEndpointMapper;
import jakarta.validation.Valid;
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
  public ResponseEntity<Void> createAuction(@Valid @RequestBody final SaveAuctionRequest request) {

    final int result = service.saveAuction(mapper.mapRequestToModel(request));

    if (result != 1) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PatchMapping(value = "/{id}/begin")
  @Override
  public ResponseEntity<Void> beginAuction(@PathVariable("id") final long auctionId) {

    service.beginAuction(auctionId);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

//  @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
//  @Override
//  private void getAllAuctions() {
//
//  }
//
//  @GetMapping(value = "/{id}/categories", produces = APPLICATION_JSON_VALUE)
//  @Override
//  private void getAuctionsByCategories() {
//
//  }
//
//  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
//  @Override
//  private void getAuction(@PathVariable("id") long id) {
//
//  }
//
//  @DeleteMapping("/remove/{id}")
//  @Override
//  private ResponseEntity<HttpStatus> deleteAuction(@PathVariable("id") long id) {
//
//    return ResponseEntity.noContent().build();
//  }
//
//  private void startAuction() {
//  }
}
