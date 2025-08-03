package com.vicangel.e_commerce_auction_server_system.core.model;

public record ItemImage(Long itemId,
                        String name,
                        String description,
                        String type,
                        byte[] image
) {
}
