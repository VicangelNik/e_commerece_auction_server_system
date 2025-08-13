package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record AuctionItemDTO(Long id,
                             @Positive Long auctionId,
                             @NotBlank String name,
                             String description,
                             String location,
                             Double latitude,
                             Double longitude,
                             String country,
                             Set<ItemCategoryDTO> categories,
                             ItemImageDTO image) {
}