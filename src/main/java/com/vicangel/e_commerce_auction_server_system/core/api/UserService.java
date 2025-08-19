package com.vicangel.e_commerce_auction_server_system.core.api;

import java.util.List;
import java.util.Optional;

import com.vicangel.e_commerce_auction_server_system.core.model.User;

public interface UserService {

  long insertUser(User user);

  Optional<User> findById(long id, boolean fetchAvatar);

  List<User> findAll(boolean fetchAvatar);

  void updateUser(long id, User user);
}
