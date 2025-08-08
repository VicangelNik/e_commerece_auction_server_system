package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto;

public record ItemImageDTO(Long itemId,
                           String name,
                           String description,
                           String type,
                           byte[] image
) {
}
