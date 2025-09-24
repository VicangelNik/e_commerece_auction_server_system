package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response;

import java.time.Instant;
import java.util.Set;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.BidDTO;

public record AuctionResponse(Long id,
                              String title,
                              Instant created,
                              Instant started,
                              Instant endDate,
                              Float firstBid,
                              Float currentBestBid,
                              Long sellerId,
                              Long categoryId,
                              Set<AuctionItemResponse> auctionItems,
                              Set<BidDTO> bids) {
}
