package com.vicangel.e_commerce_auction_server_system.endpoint.rest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveOrUpdatedUserRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.UserResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * @apiNote if I place these validations in impl class then I get the following exception
 *   jakarta.validation.ConstraintDeclarationException: HV000151:
 *   A method overriding another method must not redefine the parameter constraint configuration
 */
public interface UserOpenAPI {

  ResponseEntity<Long> addUser(SaveOrUpdatedUserRequest request);

  ResponseEntity<UserResponse> findById(@Valid @Positive long id);

  ResponseEntity<List<UserResponse>> findAll();

  ResponseEntity<Void> updateUser(@Valid @Positive long id, SaveOrUpdatedUserRequest request);
}
