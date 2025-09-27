package com.vicangel.e_commerce_auction_server_system.endpoint.rest.api;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.AuthenticationRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.RegistrationRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.AuthenticationResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Authentication", description = "Api to accept requests regarding user authentication")
public interface AuthenticationOpenAPI {

  ResponseEntity<Void> register(@Valid RegistrationRequest request);

  ResponseEntity<AuthenticationResponse> authenticate(@Valid final AuthenticationRequest request);
}
