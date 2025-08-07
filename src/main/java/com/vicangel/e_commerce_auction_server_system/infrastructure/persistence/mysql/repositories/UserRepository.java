package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories;

import java.util.Optional;
import java.util.stream.Stream;

import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.UserEntity;

public interface UserRepository {

  /**
   * @return the id of the inserted user
   */
  long insertUser(UserEntity entity);

  Optional<UserEntity> findById(long id);

  Stream<UserEntity> findAll();

  /**
   *
   * @return the number of rows affected
   */
  int updateUser(UserEntity userToUpdate);
}
