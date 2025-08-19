package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities;

import java.time.Instant;
import java.util.Set;

import lombok.Builder;

@Builder
public record UserEntity(Long id,
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
                         String country,
                         Set<Long> roles,
                         String avatar) {
}