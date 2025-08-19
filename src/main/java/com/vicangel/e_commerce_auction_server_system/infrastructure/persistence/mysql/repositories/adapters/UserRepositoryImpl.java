
package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import com.vicangel.e_commerce_auction_server_system.core.model.commons.ErrorCodes;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.UserEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

  private static final String FIND_BY_ID_SQL = "SELECT * FROM users WHERE id = ?";
  private static final String FIND_NO_AVATAR_SQL = "SELECT id, created, username, password, name, surname, email, phone, afm, bidder_rating, seller_rating, location, country FROM users";
  private static final String insertSQL = """
    INSERT INTO `auction-db`.users (created, username, password, name, surname, email, phone, afm, bidder_rating,
                                    seller_rating, location, country, avatar) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)
    """;
  private static final String FIND_ALL_SQL = "SELECT * FROM users";
  private static final String updateSQL = """
    UPDATE `auction-db`.users
    SET username = ?, password = ?, name = ?, surname = ?, email = ?, phone = ?,
        afm = ?, bidder_rating = ?, seller_rating = ?, location = ?, country = ?,
        avatar = ?
    WHERE id = ?;
    """;
  private static final String ADD_USER_ROLE_SQL = "INSERT INTO user_roles (user_id, role_name) VALUES (?, ?)";
  private static final String WHERE_ID_SQL = " WHERE id = ?";
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
    final String sql = fetchAvatar ? FIND_BY_ID_SQL : FIND_NO_AVATAR_SQL + WHERE_ID_SQL;
    try {
      return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                                                             new UserEntityRowMapper(),
                                                             id));
    } catch (EmptyResultDataAccessException e) {
      log.error(e.getMessage(), e);
      return Optional.empty();
    }
  }

  @Override
  public Stream<UserEntity> findAll(final boolean fetchAvatar) { // maybe check this https://github.com/spring-projects/spring-framework/issues/27988
    final String sql = fetchAvatar ? FIND_ALL_SQL : FIND_NO_AVATAR_SQL;
    return jdbcTemplate.queryForStream(sql, new UserEntityRowMapper());
  }

  @Override
  public int updateUser(final UserEntity userToUpdate) {
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
                               userToUpdate.country(),
                               userToUpdate.avatar(),
                               userToUpdate.id());
  }

  private static final class UserEntityRowMapper implements RowMapper<UserEntity> {

    /**
     * @param rs     the {@code ResultSet} to map (pre-initialized for the current row)
     * @param rowNum the number of the current row
     *
     * @implNote jdbc returns 0 if written like rs.getInt as it returns primitive types but written with getObject it will return null
     */
    @Override
    public UserEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
      return UserEntity.builder()
        .id(rs.getLong("id"))
        .created(rs.getTimestamp("created").toInstant())
        .username(rs.getString("username"))
        .password(rs.getString("password"))
        .name(rs.getString("name"))
        .surname(rs.getString("surname"))
        .email(rs.getString("email"))
        .phone(rs.getObject("phone", String.class))
        .afm(rs.getObject("afm", String.class))
        .bidderRating(rs.getObject("bidder_rating", Integer.class))
        .sellerRating(rs.getObject("seller_rating", Integer.class))
        .location(rs.getObject("location", String.class))
        .country(rs.getObject("country", String.class))
        .build();
    }
  }
}
