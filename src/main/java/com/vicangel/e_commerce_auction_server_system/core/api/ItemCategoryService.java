package com.vicangel.e_commerce_auction_server_system.core.api;

import java.util.List;
import java.util.Optional;

import com.vicangel.e_commerce_auction_server_system.core.model.ItemCategory;

public interface ItemCategoryService {

  long addCategoryItem(ItemCategory category);

  Optional<ItemCategory> findById(Long id);

  List<ItemCategory> findAll();
}
