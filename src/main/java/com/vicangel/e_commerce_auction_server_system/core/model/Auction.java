package com.vicangel.e_commerce_auction_server_system.core.model;

import java.time.Instant;
import java.util.Set;

import lombok.Builder;

@Builder
public record Auction(Long id,
                      Instant created,
                      Instant started,
                      Instant ends,
                      Float firstBid,
                      Float currently,
                      Integer numberOfBids,
                      Long sellerId,
                      Set<AuctionItem> auctionItems,
                      Set<Bid> bids
) {
}