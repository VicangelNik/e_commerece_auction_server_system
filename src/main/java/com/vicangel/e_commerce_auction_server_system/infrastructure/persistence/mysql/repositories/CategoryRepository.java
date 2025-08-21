package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories;

import java.util.Optional;
import java.util.stream.Stream;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.CategoryEntity;

public interface CategoryRepository {

  /**
   * @return the id of the inserted category
   */
  long insertCategory(CategoryEntity entity);

  Optional<CategoryEntity> findById(long id);

  Stream<CategoryEntity> findAll();
}
