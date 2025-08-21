package com.vicangel.e_commerce_auction_server_system.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.vicangel.e_commerce_auction_server_system.core.model.Category;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.CategoryEntity;

@Mapper(componentModel = ComponentModel.SPRING)
public interface CategoryCoreMapper {

  CategoryEntity mapModelToEntity(Category category);

  Category mapEntityToModel(CategoryEntity category);
}
