package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories;

import java.util.List;
import java.util.Optional;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.ItemCategoryEntity;

public interface CategoryItemRepository {

  /**
   * @return the id of the inserted category
   */
  long insertCategory(ItemCategoryEntity entity);

  Optional<ItemCategoryEntity> findById(long id);

  List<ItemCategoryEntity> findAll();
}
