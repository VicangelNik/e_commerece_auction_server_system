package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request.SaveUserRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.response.UserResponse;

public interface UserOpenAPI {

  ResponseEntity<Long> addUser(SaveUserRequest request);

  ResponseEntity<UserResponse> findById(long id);

  ResponseEntity<List<UserResponse>> findAll();
}
