package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto;

import java.time.Instant;

public record BidDTO(Long id,
                     Long auctionId,
                     Long bidderId,
                     Instant bidSubmittedTime,
                     Double amount) {
}