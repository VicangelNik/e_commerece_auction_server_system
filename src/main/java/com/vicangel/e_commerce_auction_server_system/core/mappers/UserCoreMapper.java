package com.vicangel.e_commerce_auction_server_system.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.vicangel.e_commerce_auction_server_system.core.model.User;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.UserEntity;

@Mapper(componentModel = ComponentModel.SPRING)
public interface UserCoreMapper {

  UserEntity mapModelToEntity(User user);

  User mapEntityToModel(UserEntity category);
}
