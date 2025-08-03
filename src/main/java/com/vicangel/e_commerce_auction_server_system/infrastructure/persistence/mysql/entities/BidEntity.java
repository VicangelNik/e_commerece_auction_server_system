package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities;

import java.time.Instant;

public record BidEntity(Long id,
                        Long auctionId,
                        Long bidderId,
                        Instant bidSubmittedTime,
                        Double amount) {
}
