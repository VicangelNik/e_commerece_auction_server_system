package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicangel.e_commerce_auction_server_system.core.api.AuthenticationService;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.api.AuthenticationOpenAPI;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.AuthenticationRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.RegistrationRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.AuthenticationResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers.UserEndpointMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
@Slf4j
class AuthenticationController implements AuthenticationOpenAPI {

  private final AuthenticationService service;
  private final UserEndpointMapper mapper;

  @PostMapping(value = "/register", consumes = APPLICATION_JSON_VALUE)
  public ResponseEntity<Void> register(@RequestBody final RegistrationRequest request) {
    service.register(mapper.mapRegistrationRequestToModel(request));
    return ResponseEntity.accepted().build();
  }

  @PostMapping(value = "/authenticate", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody final AuthenticationRequest request) {
    final String token = service.authenticate(request.email(), request.password());

    return ResponseEntity.ok(new AuthenticationResponse(token));
  }
}
