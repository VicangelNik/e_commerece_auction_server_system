package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicangel.e_commerce_auction_server_system.core.api.CategoryService;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.api.CategoryOpenAPI;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveCategoryRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.CategoryResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.IdResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers.CategoryEndpointMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
@Slf4j
final class CategoryController implements CategoryOpenAPI {

  private final CategoryEndpointMapper mapper;
  private final CategoryService service;

  @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<IdResponse> addCategory(@RequestBody final SaveCategoryRequest request) {

    final long result = service.addCategory(mapper.mapRequestToModel(request));

    return ResponseEntity.status(HttpStatus.CREATED).body(new IdResponse(result));
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<CategoryResponse> findById(@PathVariable final long id) {

    Optional<CategoryResponse> response = service.findById(id).map(mapper::mapModelToResponse);

    return response.map(r -> ResponseEntity.status(HttpStatus.OK).body(r))
      .orElseGet(() -> ResponseEntity.notFound().build());
  }

  @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<CategoryResponse>> findAll() {

    final List<CategoryResponse> response = service.findAll().stream()
      .map(mapper::mapModelToResponse)
      .toList();

    if (response.isEmpty()) return ResponseEntity.notFound().build();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
