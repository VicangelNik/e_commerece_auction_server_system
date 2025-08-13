package com.vicangel.e_commerce_auction_server_system.core.model;

import java.util.Set;

public record AuctionItem(Long id,
                          Long auctionId,
                          String name,
                          String description,
                          String location,
                          Double latitude,
                          Double longitude,
                          String country,
                          Set<ItemCategory> categories,
                          ItemImage image) {
}