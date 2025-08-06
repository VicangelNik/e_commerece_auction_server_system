
package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.adapters;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.e_commerce_auction_server_system.core.model.commons.ErrorCodes;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.UserEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.UserRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private static final String findByIdSQL = "SELECT * FROM users WHERE id = ?";
  private static final String insertSQL = """
    INSERT INTO `auction-db`.users (created, username, password, name, surname, email, phone, afm, bidder_rating,
                                    seller_rating, location, country) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)
    """;
  private static final String findAllSQL = "SELECT * FROM users";
  private static final String updateSQL = """
    UPDATE `auction-db`.users
    SET username = ?, password = ?, name = ?, surname = ?, email = ?, phone = ?,
        afm = ?, bidder_rating = ?, seller_rating = ?, location = ?, country = ?
    WHERE id = ?;
    """;
  private final JdbcTemplate jdbcTemplate;

  @Override
  public long insertUser(@NonNull final UserEntity entity) {

    final var keyHolder = new GeneratedKeyHolder();

    jdbcTemplate.update(connection -> {
      PreparedStatement ps = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
      ps.setTimestamp(1, Timestamp.from(entity.created()));
      ps.setString(2, entity.username());
      ps.setString(3, entity.password());
      ps.setString(4, entity.name());
      ps.setString(5, entity.surname());
      ps.setString(6, entity.email());
      ps.setString(7, entity.phone());
      ps.setString(8, entity.afm());
      ps.setInt(9, entity.bidderRating());
      ps.setInt(10, entity.sellerRating());
      ps.setString(11, entity.location());
      ps.setString(12, entity.country());
      return ps;
    }, keyHolder);

    if (keyHolder.getKey() == null) return ErrorCodes.SQL_ERROR.getCode();

    return keyHolder.getKey().longValue();
  }

  @Override
  public Optional<UserEntity> findById(final long id) {
    try {
      UserEntity entity = jdbcTemplate.queryForObject(
        findByIdSQL,
        new BeanPropertyRowMapper<>(UserEntity.class),
        id
      );
      return Optional.ofNullable(entity);
    } catch (EmptyResultDataAccessException e) {
      return Optional.empty();
    }
  }

  @Override
  public List<UserEntity> findAll() {
    return jdbcTemplate.queryForList(findAllSQL, UserEntity.class);
  }

  @Override
  public int updateUser(UserEntity userToUpdate) {
    return jdbcTemplate.update(updateSQL,
                               userToUpdate.username(),
                               userToUpdate.password(),
                               userToUpdate.name(),
                               userToUpdate.surname(),
                               userToUpdate.email(),
                               userToUpdate.phone(),
                               userToUpdate.afm(),
                               userToUpdate.bidderRating(),
                               userToUpdate.sellerRating(),
                               userToUpdate.location(),
                               userToUpdate.country());
  }
}
