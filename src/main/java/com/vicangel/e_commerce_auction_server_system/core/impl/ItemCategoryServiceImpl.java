package com.vicangel.e_commerce_auction_server_system.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.vicangel.e_commerce_auction_server_system.core.api.ItemCategoryService;
import com.vicangel.e_commerce_auction_server_system.core.mappers.ItemCategoryCoreMapper;
import com.vicangel.e_commerce_auction_server_system.core.model.ItemCategory;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.CategoryItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
final class ItemCategoryServiceImpl implements ItemCategoryService {

  private final CategoryItemRepository categoryItemRepository;
  private final ItemCategoryCoreMapper itemCategoryCoreMapper;

  @Override
  public long addCategoryItem(ItemCategory category) {
    return categoryItemRepository.insertCategory(itemCategoryCoreMapper.mapModelToEntity(category));
  }

  @Override
  public Optional<ItemCategory> findById(Long id) {
    return categoryItemRepository
      .findById(id)
      .map(itemCategoryCoreMapper::mapEntityToModel);
  }

  @Override
  public List<ItemCategory> findAll() {
    return categoryItemRepository
      .findAll()
      .stream()
      .map(itemCategoryCoreMapper::mapEntityToModel)
      .toList();
  }
}
