package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities;

public record AuctionItemImageEntity(Long itemId,
                                     String name,
                                     String description,
                                     String type,
                                     byte[] image) {
}
