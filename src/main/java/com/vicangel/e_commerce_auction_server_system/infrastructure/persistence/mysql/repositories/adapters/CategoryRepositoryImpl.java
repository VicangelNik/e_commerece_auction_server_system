package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.e_commerce_auction_server_system.core.model.commons.ErrorCodes;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.CategoryEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {

  private static final String FIND_BY_ID_SQL = "SELECT * FROM categories WHERE id = ?";
  private static final String INSERT_SQL = "INSERT INTO categories (name, description) VALUES (?,?)";
  private static final String FIND_ALL_SQL = "SELECT * FROM categories";
  private final JdbcTemplate jdbcTemplate;

  @Override
  public long insertCategory(@NonNull final CategoryEntity entity) {

    final var keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);
      ps.setString(1, entity.name());
      ps.setString(2, entity.description());
      return ps;
    }, keyHolder);

    if (keyHolder.getKey() != null) return keyHolder.getKey().longValue();

    return ErrorCodes.SQL_ERROR.getCode();
  }

  @Override
  public Optional<CategoryEntity> findById(final long id) {
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(
        FIND_BY_ID_SQL,
        new CategoryEntityRowMapper(),
        id
      ));
    } catch (EmptyResultDataAccessException e) {
      log.error(e.getMessage(), e);
      return Optional.empty();
    }
  }

  @Override
  public Stream<CategoryEntity> findAll() {
    return jdbcTemplate.queryForStream(FIND_ALL_SQL, new CategoryEntityRowMapper());
  }

  private static final class CategoryEntityRowMapper implements RowMapper<CategoryEntity> {

    /**
     * @param rs     the {@code ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     **/
    @Override
    public CategoryEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
      return new CategoryEntity(rs.getLong("id"),
                                rs.getString("name"),
                                rs.getString("description"));
    }
  }
}
