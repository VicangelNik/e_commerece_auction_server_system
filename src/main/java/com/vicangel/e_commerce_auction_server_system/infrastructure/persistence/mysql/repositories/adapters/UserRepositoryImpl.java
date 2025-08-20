
package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.adapters;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.e_commerce_auction_server_system.core.error.RoleNotValidException;
import com.vicangel.e_commerce_auction_server_system.core.model.commons.ErrorCodes;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.UserEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.UserRepository;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.helpers.UserEntityResultSetExtractor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

  private static final String insertSQL = """
    INSERT INTO `auction-db`.users (created, username, password, name, surname, email, phone, afm, bidder_rating,
                                    seller_rating, location, country, avatar) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
    """;
  private static final String FIND_ALL_SQL = """
    SELECT u.id,
           u.created,
           u.username,
           u.password,
           u.name,
           u.surname,
           u.email,
           u.phone,
           u.afm,
           u.bidder_rating,
           u.seller_rating,
           u.location,
           u.country,
           u.avatar,
           r.name
    FROM `auction-db`.users u
             INNER JOIN `auction-db`.user_roles ur ON ur.user_id = u.id
             INNER JOIN `auction-db`.roles r ON r.name = ur.role_name
    """;
  private static final String UPDATE_SQL = """
    UPDATE `auction-db`.users
    SET username = ?, password = ?, name = ?, surname = ?, email = ?, phone = ?,
        afm = ?, bidder_rating = ?, seller_rating = ?, location = ?, country = ?,
        avatar = ?
    WHERE id = ?;
    """;
  private static final String ADD_USER_ROLE_SQL = "INSERT INTO user_roles (user_id, role_name) VALUES (?, ?)";
  private static final String WHERE_ID_SQL = " WHERE id = ?";

  private final JdbcTemplate jdbcTemplate;
  private final UserEntityResultSetExtractor extractor;

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
      ps.setObject(7, entity.phone(), Types.VARCHAR); // might be null, hence, this differentiation.
      ps.setObject(8, entity.afm(), Types.VARCHAR);
      ps.setObject(9, entity.bidderRating(), Types.INTEGER);
      ps.setObject(10, entity.sellerRating(), Types.INTEGER);
      ps.setObject(11, entity.location(), Types.VARCHAR);
      ps.setObject(12, entity.country(), Types.VARCHAR);
      ps.setObject(13, entity.avatar(), Types.BLOB);
      return ps;
    }, keyHolder);

    if (keyHolder.getKey() == null) return ErrorCodes.SQL_ERROR.getCode();

    final long userId = keyHolder.getKey().longValue();

    entity.roles()
      .forEach(roleId -> {

        int addUserRoleResult = jdbcTemplate.update(ADD_USER_ROLE_SQL, userId, roleId);

        if (addUserRoleResult != 1) log.error("Role {} could not be added to User {}", roleId, userId);
      });

    return userId;
  }

  @Override
  public Optional<UserEntity> findById(final long id, final boolean fetchAvatar) {
    String sql = FIND_ALL_SQL + WHERE_ID_SQL;

    if (!fetchAvatar) {
      sql = sql.replace("u.avatar,", ""); // exclude fetching image
    }
    final List<UserEntity> l = jdbcTemplate.query(sql, extractor, id);

    if (l == null || l.isEmpty()) return Optional.empty();

    try {
      return Optional.of(l.getFirst());
    } catch (EmptyResultDataAccessException e) {
      log.error(e.getMessage(), e);
      return Optional.empty();
    }
  }

  @Override
  public List<UserEntity> findAll(final boolean fetchAvatar) { // maybe check this https://github.com/spring-projects/spring-framework/issues/27988
    String sql = FIND_ALL_SQL;
    if (!fetchAvatar) {
      sql = sql.replace("u.avatar,", ""); // exclude fetching image
    }
    return jdbcTemplate.query(sql, extractor);
  }

  @Override
  public int updateUser(final UserEntity userToUpdate) {
    userToUpdate.roles().forEach(roleId -> {
      try {
        int addUserRoleResult = jdbcTemplate.update(ADD_USER_ROLE_SQL, userToUpdate.id(), roleId);
        if (addUserRoleResult != 1) log.error("Role {} could not be added to User {}", roleId, userToUpdate.id());
      }catch (DataIntegrityViolationException e)
      {
        throw new RoleNotValidException("Role to be added for user does not exist", e);
      }
    });

    return jdbcTemplate.update(UPDATE_SQL,
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
                               userToUpdate.country(),
                               userToUpdate.avatar(),
                               userToUpdate.id());
  }
}
