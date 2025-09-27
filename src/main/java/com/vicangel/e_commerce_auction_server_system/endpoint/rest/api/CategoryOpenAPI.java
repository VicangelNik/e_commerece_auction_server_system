package com.vicangel.e_commerce_auction_server_system.endpoint.rest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveCategoryRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.CategoryResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.IdResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * @apiNote if I place these validations in impl class then I get the following exception
 *   jakarta.validation.ConstraintDeclarationException: HV000151:
 *   A method overriding another method must not redefine the parameter constraint configuration
 */
@Tag(name = "Category", description = "Api to accept requests regarding category operations")
public interface CategoryOpenAPI {

  ResponseEntity<IdResponse> addCategory(@Valid SaveCategoryRequest request);

  ResponseEntity<CategoryResponse> findById(@Valid @Positive long id);

  ResponseEntity<List<CategoryResponse>> findAll();
}
