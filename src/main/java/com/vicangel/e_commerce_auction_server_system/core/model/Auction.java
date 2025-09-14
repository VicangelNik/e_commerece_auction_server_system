package com.vicangel.e_commerce_auction_server_system.core.model;

import java.time.Instant;
import java.util.Set;

import lombok.Builder;

@Builder
public record Auction(Long id,
                      String title,
                      Instant created,
                      Instant started,
                      Instant endDate,
                      Float firstBid,
                      Float currentBestBid,
                      Integer numberOfBids,
                      Long sellerId,
                      Long categoryId,
                      Set<AuctionItem> auctionItems,
                      Set<Bid> bids
) {
}