package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record SaveBidRequest(@NotNull @Positive Long auctionId,
                             @NotNull @Positive Long bidderId,
                             @NotNull @Positive Double amount) {
}