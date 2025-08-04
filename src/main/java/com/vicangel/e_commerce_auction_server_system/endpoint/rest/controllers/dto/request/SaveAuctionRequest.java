package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request;

import java.time.Instant;
import java.util.Set;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.AuctionItemDTO;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.BidDTO;
import lombok.Builder;

@Builder
public record SaveAuctionRequest(Instant ends,
                                 Float firstBid,
                                 Integer numberOfBids,
                                 Long sellerId,
                                 Set<AuctionItemDTO> auctionItems,
                                 Set<BidDTO> bids) {
}