package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities;

import java.time.Instant;
import java.util.Set;

import lombok.Builder;

@Builder
public record AuctionEntity(Long id,
                            Instant created,
                            Instant started,
                            Instant ends,
                            Float firstBid,
                            Float currently,
                            Integer numberOfBids,
                            Long sellerId,
                            Set<AuctionItemEntity> auctionItems,
                            Set<BidEntity> bids) {
}
