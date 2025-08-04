package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.adapters;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.ItemCategoryEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.CategoryItemRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryItemRepositoryImpl implements CategoryItemRepository {

  private static final String findByIdSQL = "SELECT * FROM categories WHERE id = ?";
  private static final String insertSQL = "INSERT INTO categories (name, description) VALUES (?,?)";
  private static final String findAllSQL = "SELECT * FROM categories";
  private final JdbcTemplate jdbcTemplate;

  @Override
  public long insertCategory(@NonNull final ItemCategoryEntity entity) {

    final var keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, entity.name());
      ps.setString(2, entity.description());
      return ps;
    }, keyHolder);

    if (keyHolder.getKey() != null) return keyHolder.getKey().longValue();

    return 0;
  }

  @Override
  public Optional<ItemCategoryEntity> findById(final long id) {
    try {
      ItemCategoryEntity entity = jdbcTemplate.queryForObject(
        findByIdSQL,
        new BeanPropertyRowMapper<>(ItemCategoryEntity.class),
        id
      );
      return Optional.ofNullable(entity);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<ItemCategoryEntity> findAll() {
    return jdbcTemplate.queryForList(findAllSQL, ItemCategoryEntity.class);
  }
}
