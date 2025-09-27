package com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers;

import java.io.IOException;
import java.util.Base64;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import com.vicangel.e_commerce_auction_server_system.core.error.UserException;
import com.vicangel.e_commerce_auction_server_system.core.model.User;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.RegistrationRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveOrUpdatedUserRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.UserResponse;
import lombok.Setter;

@Mapper(componentModel = ComponentModel.SPRING, imports = Base64.class)
public abstract class UserEndpointMapper {

  @Setter(onMethod_ = @Autowired)
  protected PasswordEncoder passwordEncoder;

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "accountLocked", ignore = true)
  @Mapping(target = "enabled", ignore = true)
  @Mapping(target = "name", source = "request.name")
  @Mapping(target = "avatar", expression = "java( getEncodedImage(avatar) )")
  public abstract User mapRequestToModel(SaveOrUpdatedUserRequest request, MultipartFile avatar);

  @Mapping(target = "withAvatar", ignore = true)
  public abstract UserResponse mapModelToResponse(User user);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "bidderRating", ignore = true)
  @Mapping(target = "sellerRating", ignore = true)
  @Mapping(target = "avatar", ignore = true)
  @Mapping(target = "password", expression = "java( passwordEncoder.encode(request.password()) )")
  @Mapping(target = "accountLocked", constant = "false")
  @Mapping(target = "enabled", constant = "true")
  public abstract User mapRegistrationRequestToModel(RegistrationRequest request);

  protected String getEncodedImage(final MultipartFile avatar) {
    try {
      if (avatar == null) return null;
      return Base64.getEncoder().encodeToString(avatar.getBytes());
    } catch (IOException io) {
      throw new UserException("User's image could not be processed", io);
    }
  }
}
