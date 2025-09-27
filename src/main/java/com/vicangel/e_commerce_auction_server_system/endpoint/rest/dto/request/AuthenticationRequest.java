package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthenticationRequest(
  @Email(message = "Email is not well formatted") @NotBlank(message = "Email is mandatory") String email,
  @NotBlank @Size(min = 8, message = "Password should be 8 characters long minimum") String password) {
}
