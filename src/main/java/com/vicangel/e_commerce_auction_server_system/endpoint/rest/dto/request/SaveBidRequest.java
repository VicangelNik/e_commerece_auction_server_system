package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request;

import java.time.Instant;

public record SaveBidRequest(Long auctionId,
                             Long bidderId,
                             Instant bidSubmittedTime,
                             Double amount) {
}