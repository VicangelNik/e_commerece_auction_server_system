package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.vicangel.e_commerce_auction_server_system.core.api.UserService;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.api.UserOpenAPI;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveOrUpdatedUserRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.IdResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.UserResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers.UserEndpointMapper;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.validation.SaveUser;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.validation.UpdateUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RequiredArgsConstructor
@RequestMapping("/user")
@RestController
@Validated
@Slf4j
class UserController implements UserOpenAPI {

  private final UserEndpointMapper mapper;
  private final UserService service;

  @PostMapping(value = "/add", consumes = MULTIPART_FORM_DATA_VALUE, produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<IdResponse> addUser(@Validated(SaveUser.class) @RequestPart("request") final SaveOrUpdatedUserRequest request,
                                            @RequestPart(value = "avatar", required = false) MultipartFile avatar) {

    final long result = service.insertUser(mapper.mapRequestToModel(request, avatar));

    return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(result));
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<UserResponse> findById(@PathVariable final long id,
                                               @RequestParam("fetchAvatar") final boolean fetchAvatar) {

    Optional<UserResponse> response = service.findById(id, fetchAvatar).map(mapper::mapModelToResponse);

    return response.map(r -> ResponseEntity.status(HttpStatus.OK).body(r))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<UserResponse>> findAll(@RequestParam("fetchAvatar") final boolean fetchAvatar) {

    final List<UserResponse> response = service.findAll(fetchAvatar).stream().map(mapper::mapModelToResponse).toList();

    if (response.isEmpty()) return ResponseEntity.notFound().build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PutMapping(value = "/{id}", consumes = MULTIPART_FORM_DATA_VALUE)
  @Override
  public ResponseEntity<Void> updateUser(@PathVariable final long id,
                                         @Validated(UpdateUser.class) @RequestPart final SaveOrUpdatedUserRequest request,
                                         @RequestPart(value = "avatar", required = false) MultipartFile avatar) {

    service.updateUser(id, mapper.mapRequestToModel(request, avatar));

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
