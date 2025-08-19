package com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers;

import java.io.IOException;
import java.util.Base64;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.web.multipart.MultipartFile;

import com.vicangel.e_commerce_auction_server_system.core.error.UserException;
import com.vicangel.e_commerce_auction_server_system.core.model.User;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveOrUpdatedUserRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.UserResponse;

@Mapper(componentModel = ComponentModel.SPRING, imports = Base64.class)
public interface UserEndpointMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "name", source = "request.name")
  @Mapping(target = "avatar", expression = "java( getEncodedImage(avatar) )")
  User mapRequestToModel(SaveOrUpdatedUserRequest request, MultipartFile avatar);

  UserResponse mapModelToResponse(User user);

  default String getEncodedImage(final MultipartFile avatar) {
    try {
      if (avatar == null) return null;
      return Base64.getEncoder().encodeToString(avatar.getBytes());
    } catch (IOException io) {
      throw new UserException("User's image could not be processed", io);
    }
  }
}
