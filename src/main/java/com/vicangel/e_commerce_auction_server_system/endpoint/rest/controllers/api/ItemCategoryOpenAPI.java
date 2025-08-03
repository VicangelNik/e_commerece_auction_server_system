package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.api;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request.SaveItemCategoryRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.response.ItemCategoryResponse;

public interface ItemCategoryOpenAPI {

  ResponseEntity<Long> addCategory(SaveItemCategoryRequest request);

  ResponseEntity<ItemCategoryResponse> findById(long id);

  ResponseEntity<List<ItemCategoryResponse>> getAllAuctions();
}
