package com.vicangel.e_commerce_auction_server_system.core.api;

import org.springframework.transaction.annotation.Transactional;

import com.vicangel.e_commerce_auction_server_system.core.model.User;

public interface AuthenticationService {

  @Transactional
  void register(User user);

  String authenticate(String email, String password);
}
