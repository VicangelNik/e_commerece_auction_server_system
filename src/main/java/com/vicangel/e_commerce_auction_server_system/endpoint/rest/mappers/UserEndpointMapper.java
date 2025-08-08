package com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.vicangel.e_commerce_auction_server_system.core.model.User;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveOrUpdatedUserRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.UserResponse;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserEndpointMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  User mapRequestToModel(SaveOrUpdatedUserRequest request);

  UserResponse mapModelToResponse(User user);
}
