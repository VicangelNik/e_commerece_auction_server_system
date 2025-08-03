package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request;

import java.util.Set;

public record AuctionItemDTO(Long id,
                             Long auctionId,
                             String name,
                             String description,
                             String location,
                             Double latitude,
                             Double longitude,
                             String country,
                             Set<Long> categories) {
}