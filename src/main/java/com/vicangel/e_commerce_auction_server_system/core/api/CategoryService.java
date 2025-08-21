package com.vicangel.e_commerce_auction_server_system.core.api;

import java.util.List;
import java.util.Optional;

import com.vicangel.e_commerce_auction_server_system.core.model.Category;

public interface CategoryService {

  long addCategory(Category category);

  Optional<Category> findById(long id);

  List<Category> findAll();
}
