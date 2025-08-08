package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.validation.SaveUser;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.validation.UpdateUser;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SaveOrUpdatedUserRequest(@NotBlank(groups = SaveUser.class) String username,
                                       @NotBlank(groups = SaveUser.class) String password,
                                       @NotBlank(groups = SaveUser.class) String name,
                                       @NotBlank(groups = SaveUser.class) String surname,
                                       @Email(groups = {SaveUser.class, UpdateUser.class}) String email,
                                       String phone,
                                       String afm,
                                       Integer bidderRating,
                                       Integer sellerRating,
                                       String location,
                                       String country) {
}