package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record AuctionItemRequest(Long id,
                                 @Positive Long auctionId,
                                 @NotBlank String name,
                                 String description,
                                 String location,
                                 Double latitude,
                                 Double longitude,
                                 String country,
                                 @NotEmpty Set<Long> categories) {
}