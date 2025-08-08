package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vicangel.e_commerce_auction_server_system.core.api.ItemCategoryService;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.api.ItemCategoryOpenAPI;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveItemCategoryRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.ItemCategoryResponse;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers.ItemCategoryEndpointMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@RequestMapping("/category")
@RestController
@Slf4j
final class ItemCategoryController implements ItemCategoryOpenAPI {

  private final ItemCategoryEndpointMapper mapper;
  private final ItemCategoryService service;

  @PostMapping(value = "/add", consumes = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<Long> addCategory(@Valid @RequestBody final SaveItemCategoryRequest request) {

    final long result = service.addCategoryItem(mapper.mapRequestToModel(request));

    if (result == 0) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<ItemCategoryResponse> findById(@PathVariable final long id) {

    ItemCategoryResponse response = service.findById(id).map(mapper::mapModelToResponse)
      .orElseThrow(() -> new IllegalArgumentException("Not Found"));

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
  @Override
  public ResponseEntity<List<ItemCategoryResponse>> findAll() {

    List<ItemCategoryResponse> response = service.findAll().stream().map(mapper::mapModelToResponse).toList();

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}
