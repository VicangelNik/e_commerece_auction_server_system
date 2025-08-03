package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.vicangel.e_commerce_auction_server_system.core.model.ItemCategory;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request.SaveItemCategoryRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.response.ItemCategoryResponse;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ItemCategoryEndpointMapper {

  @Mapping(target = "id", ignore = true)
  ItemCategory mapRequestToModel(SaveItemCategoryRequest request);

  ItemCategoryResponse mapModelToResponse(ItemCategory category);
}
