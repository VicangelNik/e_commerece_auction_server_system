package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request;

import java.time.Instant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record SaveAuctionRequest(@NotNull Instant ends,
                                 Float firstBid,
                                 @NotNull @Positive Integer numberOfBids,
                                 @NotNull @Positive Long sellerId,
                                 @NotNull @Positive Long categoryId) {
}