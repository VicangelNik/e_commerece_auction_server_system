package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(
  @NotBlank(message = "Firstname is mandatory") String name,
  @NotBlank(message = "Lastname is mandatory") String surname,
  @Email(message = "Email is not well formatted") @NotBlank(message = "Email is mandatory") String email,
  @NotBlank @Size(min = 8, message = "Password should be 8 characters long minimum") String password,
  String phone,
  String afm,
  String location,
  String country,
  @NotEmpty(message = "A user must have a role") Set<String> roles) {
}