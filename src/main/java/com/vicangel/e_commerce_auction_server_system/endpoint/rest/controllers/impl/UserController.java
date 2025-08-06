package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.impl;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicangel.e_commerce_auction_server_system.core.api.UserService;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.api.UserOpenAPI;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request.SaveUserRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.response.UserResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.mappers.UserEndpointMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
@Slf4j
final class UserController implements UserOpenAPI {

  private final UserEndpointMapper mapper;
  private final UserService service;

  @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<Long> addUser(@Valid @RequestBody final SaveUserRequest request) {

    final long result = service.insertUser(mapper.mapRequestToModel(request));

    if (result == 0) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserResponse> findById(@PathVariable final long id) {

    UserResponse response = service.findById(id).map(mapper::mapModelToResponse)
      .orElseThrow(() -> new IllegalArgumentException("Not Found"));

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<UserResponse>> findAll() {

    List<UserResponse> response = service.findAll().stream().map(mapper::mapModelToResponse).toList();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<Void> updateUser(@PathVariable final long id,
                                         @RequestBody final SaveUserRequest request) {

    service.updateUser(id, mapper.mapRequestToModel(request));

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
