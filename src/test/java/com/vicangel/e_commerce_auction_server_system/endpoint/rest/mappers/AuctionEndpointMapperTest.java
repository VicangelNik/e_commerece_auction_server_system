package com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers;

import java.time.Instant;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.vicangel.e_commerce_auction_server_system.core.model.Auction;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveAuctionRequest;

final class AuctionEndpointMapperTest {

  private static AuctionEndpointMapper mapper;

  @BeforeAll
  static void beforeAll() {
    mapper = new AuctionEndpointMapperImpl();
  }

  @Test
  void mapRequestToModel() {

    final Instant ends = Instant.now();

    final Auction expected = Auction.builder().endDate(ends).firstBid(7.00F).numberOfBids(3).sellerId(1L).build();

    final Auction actual = mapper.mapRequestToModel(SaveAuctionRequest.builder().endDate(ends)
                                                      .firstBid(7.00F)
                                                      .numberOfBids(3)
                                                      .sellerId(1L)
                                                      .build());
    Assertions.assertThat(actual).isEqualTo(expected);
  }
}