package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response;

import java.util.Set;

public record AuctionItemResponse(Long id,
                                  Long auctionId,
                                  String name,
                                  String description,
                                  String location,
                                  Double latitude,
                                  Double longitude,
                                  String country,
                                  Set<Long> categories,
                                  byte[] image) {
}