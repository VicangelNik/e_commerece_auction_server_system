package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request;

public record SaveUserRequest(String username,
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