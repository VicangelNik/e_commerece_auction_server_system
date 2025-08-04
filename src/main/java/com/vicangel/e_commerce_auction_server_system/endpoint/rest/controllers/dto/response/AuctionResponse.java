package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.response;

import java.time.Instant;
import java.util.Set;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.AuctionItemDTO;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.BidDTO;

public record AuctionResponse(Long id,
                              Instant created,
                              Instant started,
                              Instant ends,
                              Float firstBid,
                              Float currently,
                              Integer numberOfBids,
                              Long sellerId,
                              Set<AuctionItemDTO> auctionItems,
                              Set<BidDTO> bids) {
}
