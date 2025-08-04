package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.vicangel.e_commerce_auction_server_system.core.model.User;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request.SaveUserRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.response.UserResponse;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserEndpointMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  User mapRequestToModel(SaveUserRequest request);

  UserResponse mapModelToResponse(User user);
}
