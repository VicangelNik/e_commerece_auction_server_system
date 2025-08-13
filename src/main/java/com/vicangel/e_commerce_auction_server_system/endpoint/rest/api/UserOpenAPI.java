package com.vicangel.e_commerce_auction_server_system.endpoint.rest.api;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveOrUpdatedUserRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.IdResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.UserResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

/**
 * @apiNote if I place these validations in impl class then I get the following exception
 *   jakarta.validation.ConstraintDeclarationException: HV000151:
 *   A method overriding another method must not redefine the parameter constraint configuration
 */
@Tag(name = "User Controller", description = "Api to accept requests regarding user operations")
public interface UserOpenAPI {

  @Operation(summary = "Add new user to system", operationId = "UserAdd", tags = "UserApi")
  @ApiResponses(value = {
    @ApiResponse(responseCode = "201",
      description = "Successful response (see examples)",
      content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
        schema = @Schema(implementation = IdResponse.class),
        examples = {
          @ExampleObject(
            name = "Generic Response",
            value = """
              {
                 "savedId": 2
               }
              """
          )})}),
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(hidden = true)))
  })
  ResponseEntity<IdResponse> addUser(SaveOrUpdatedUserRequest request);

  ResponseEntity<UserResponse> findById(@Valid @Positive long id);

  ResponseEntity<List<UserResponse>> findAll();

  ResponseEntity<Void> updateUser(@Valid @Positive long id, SaveOrUpdatedUserRequest request);
}
