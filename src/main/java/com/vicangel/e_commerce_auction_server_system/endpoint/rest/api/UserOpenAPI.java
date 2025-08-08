package com.vicangel.e_commerce_auction_server_system.endpoint.rest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveOrUpdatedUserRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.UserResponse;

public interface UserOpenAPI {

  ResponseEntity<Long> addUser(SaveOrUpdatedUserRequest request);

  ResponseEntity<UserResponse> findById(long id);

  ResponseEntity<List<UserResponse>> findAll();

  ResponseEntity<Void> updateUser(long id, SaveOrUpdatedUserRequest request);
}
