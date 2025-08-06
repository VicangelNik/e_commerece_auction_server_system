package com.vicangel.e_commerce_auction_server_system.core.model;

import java.time.Instant;

import lombok.Builder;

@Builder
public record User(Long id,
                   Instant created,
                   String username,
                   String password,
                   String name,
                   String surname,
                   String email,
                   String phone,
                   String afm,
                   Integer bidderRating,
                   Integer sellerRating,
                   String location,
                   String country) {
}