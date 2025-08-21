package com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.vicangel.e_commerce_auction_server_system.core.model.Category;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveCategoryRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.CategoryResponse;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CategoryEndpointMapper {

  @Mapping(target = "id", ignore = true)
  Category mapRequestToModel(SaveCategoryRequest request);

  CategoryResponse mapModelToResponse(Category category);
}
