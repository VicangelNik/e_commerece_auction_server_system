package com.vicangel.e_commerce_auction_server_system.core.model;

import java.time.Instant;

public record Bid(Long id,
                  Long auctionId,
                  Long bidderId,
                  Instant bidSubmittedTime,
                  Double amount) {
}