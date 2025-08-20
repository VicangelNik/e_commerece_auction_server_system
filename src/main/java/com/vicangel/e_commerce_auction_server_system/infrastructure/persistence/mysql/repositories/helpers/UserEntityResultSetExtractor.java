package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLSyntaxErrorException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.UserEntity;

import static com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.helpers.RepoUtils.toInstant;

@Component
public final class UserEntityResultSetExtractor implements ResultSetExtractor<List<UserEntity>> {

  @Override
  public List<UserEntity> extractData(@NonNull final ResultSet rs) throws SQLException {
    Map<Long, UserEntity> userEntityMap = new HashMap<>();

    while (rs.next()) {
      final Long userID = rs.getLong("u.id");

      if (userEntityMap.containsKey(userID)) { // user already exists

        final UserEntity entity = userEntityMap.get(userID);

        addRoleToUser(rs, entity);
      } else { // new user entry

        String avatar = null;
        try {
          avatar = rs.getString("u.avatar");
        } catch (SQLSyntaxErrorException e) {
          // ignore because "Column 'u.avatar' not found " occurs when we do not fetch avatar and is removed from sql
        }

        final var entity = UserEntity.builder()
          .id(userID)
          .created(toInstant(rs, "u.created"))
          .username(rs.getString("u.username"))
          .password(rs.getString("u.password"))
          .name(rs.getString("u.name"))
          .surname(rs.getString("u.surname"))
          .email(rs.getString("u.email"))
          .phone(rs.getString("u.phone"))
          .afm(rs.getString("u.afm"))
          .bidderRating(rs.getInt("u.bidder_rating"))
          .sellerRating(rs.getInt("u.seller_rating"))
          .location(rs.getString("u.location"))
          .country(rs.getString("u.country"))
          .roles(new HashSet<>())
          .avatar(avatar)
          .build();

        addRoleToUser(rs, entity);

        userEntityMap.put(userID, entity);
      }
    }
    return new ArrayList<>(userEntityMap.values());
  }

  private void addRoleToUser(@NonNull final ResultSet rs, @NonNull final UserEntity entity) throws SQLException {
    Optional.ofNullable(rs.getString("r.name"))  // might be null
      .ifPresent((role -> entity.roles().add(role)));
  }
}
