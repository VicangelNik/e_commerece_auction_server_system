package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request;

import java.util.Set;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.validation.SaveUser;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.validation.UpdateUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

public record SaveOrUpdatedUserRequest(@NotBlank(groups = SaveUser.class) String username,
                                       @NotBlank(groups = SaveUser.class) String password,
                                       @NotBlank(groups = SaveUser.class) String name,
                                       @NotBlank(groups = SaveUser.class) String surname,
                                       @Email(groups = {SaveUser.class, UpdateUser.class}) String email,
                                       String phone,
                                       String afm,
                                       @Positive(groups = {SaveUser.class, UpdateUser.class}) Integer bidderRating,
                                       @Positive(groups = {SaveUser.class, UpdateUser.class}) Integer sellerRating,
                                       String location,
                                       String country,
                                       @NotEmpty(groups = SaveUser.class) Set<String> roles) {
}