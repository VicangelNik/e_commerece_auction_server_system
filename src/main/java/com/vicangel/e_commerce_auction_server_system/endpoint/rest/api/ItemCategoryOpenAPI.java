package com.vicangel.e_commerce_auction_server_system.endpoint.rest.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveItemCategoryRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.ItemCategoryResponse;

public interface ItemCategoryOpenAPI {

  ResponseEntity<Long> addCategory(SaveItemCategoryRequest request);

  ResponseEntity<ItemCategoryResponse> findById(long id);

  ResponseEntity<List<ItemCategoryResponse>> findAll();
}
