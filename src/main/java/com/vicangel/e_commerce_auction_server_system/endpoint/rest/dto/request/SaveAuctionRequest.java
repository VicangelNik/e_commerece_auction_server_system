package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request;

import java.time.Instant;
import java.util.Set;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.AuctionItemDTO;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.BidDTO;
import lombok.Builder;

@Builder
public record SaveAuctionRequest(Instant ends,
                                 Float firstBid,
                                 Integer numberOfBids,
                                 Long sellerId,
                                 Set<AuctionItemDTO> auctionItems,
                                 Set<BidDTO> bids) {
}