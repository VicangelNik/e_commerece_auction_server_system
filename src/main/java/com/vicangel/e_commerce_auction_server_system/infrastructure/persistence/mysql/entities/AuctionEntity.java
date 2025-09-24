package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities;

import java.time.Instant;
import java.util.Set;

import lombok.Builder;

@Builder
public record AuctionEntity(Long id,
                            String title,
                            Instant created,
                            Instant started,
                            Instant endDate,
                            Float firstBid,
                            Float currentBestBid,
                            Long sellerId,
                            Long categoryId,
                            Set<AuctionItemEntity> auctionItems,
                            Set<BidEntity> bids) {
}
