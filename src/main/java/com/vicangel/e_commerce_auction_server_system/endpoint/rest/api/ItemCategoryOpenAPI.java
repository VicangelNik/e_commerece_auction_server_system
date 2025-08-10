package com.vicangel.e_commerce_auction_server_system.endpoint.rest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveItemCategoryRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.IdResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.ItemCategoryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * @apiNote if I place these validations in impl class then I get the following exception
 *   jakarta.validation.ConstraintDeclarationException: HV000151:
 *   A method overriding another method must not redefine the parameter constraint configuration
 */
public interface ItemCategoryOpenAPI {

  ResponseEntity<IdResponse> addCategory(@Valid SaveItemCategoryRequest request);

  ResponseEntity<ItemCategoryResponse> findById(@Valid @Positive long id);

  ResponseEntity<List<ItemCategoryResponse>> findAll();
}
