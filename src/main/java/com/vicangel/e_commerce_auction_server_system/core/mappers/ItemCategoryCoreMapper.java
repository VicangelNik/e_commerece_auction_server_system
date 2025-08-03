package com.vicangel.e_commerce_auction_server_system.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants.ComponentModel;

import com.vicangel.e_commerce_auction_server_system.core.model.ItemCategory;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.ItemCategoryEntity;

@Mapper(componentModel = ComponentModel.SPRING)
public interface ItemCategoryCoreMapper {

  ItemCategoryEntity mapModelToEntity(ItemCategory category);

  ItemCategory mapEntityToModel(ItemCategoryEntity category);
}
