package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities;

import java.util.Set;

import lombok.Builder;

@Builder
public record AuctionItemEntity(Long id,
                                Long auctionId,
                                String name,
                                String description,
                                String location,
                                Double latitude,
                                Double longitude,
                                String country,
                                Set<Long> categories,
                                ItemImageEntity image) {
}
