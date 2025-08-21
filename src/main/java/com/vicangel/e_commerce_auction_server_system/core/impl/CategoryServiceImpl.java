package com.vicangel.e_commerce_auction_server_system.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.vicangel.e_commerce_auction_server_system.core.api.CategoryService;
import com.vicangel.e_commerce_auction_server_system.core.error.ItemCategoryException;
import com.vicangel.e_commerce_auction_server_system.core.mappers.CategoryCoreMapper;
import com.vicangel.e_commerce_auction_server_system.core.model.Category;
import com.vicangel.e_commerce_auction_server_system.core.model.commons.ErrorCodes;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
final class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryCoreMapper categoryCoreMapper;

  @Override
  public long addCategory(final @NonNull Category category) {
    final long id = categoryRepository.insertCategory(categoryCoreMapper.mapModelToEntity(category));

    if (id == ErrorCodes.SQL_ERROR.getCode()) {
      throw new ItemCategoryException("Error occurred, inserting category item");
    }

    return id;
  }

  @Override
  public Optional<Category> findById(final long id) {
    return categoryRepository
      .findById(id)
      .map(categoryCoreMapper::mapEntityToModel);
  }

  @Override
  public List<Category> findAll() {
    return categoryRepository
      .findAll()
      .map(categoryCoreMapper::mapEntityToModel)
      .toList();
  }
}
