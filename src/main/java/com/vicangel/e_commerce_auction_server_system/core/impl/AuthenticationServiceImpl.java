package com.vicangel.e_commerce_auction_server_system.core.impl;

import java.util.HashMap;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.vicangel.e_commerce_auction_server_system.core.api.AuthenticationService;
import com.vicangel.e_commerce_auction_server_system.core.error.UserException;
import com.vicangel.e_commerce_auction_server_system.core.mappers.UserCoreMapper;
import com.vicangel.e_commerce_auction_server_system.core.model.User;
import com.vicangel.e_commerce_auction_server_system.core.model.commons.ErrorCodes;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.security.JwtService;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.UserRepository;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class AuthenticationServiceImpl implements AuthenticationService {

  private final UserRepository userRepository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserCoreMapper userCoreMapper;

  public void register(@Nonnull final User user) {
    final long id = userRepository.insertUser(userCoreMapper.mapModelToEntity(user));

    if (id == ErrorCodes.SQL_ERROR.getCode()) {
      throw new UserException("Error occurred, inserting user item");
    }
    // sendValidationEmail(user); TODO validate with email
  }

  @Override
  public String authenticate(@Nonnull final String email, @Nonnull final String password) {
    var auth = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(email, password)
    );
    final var claims = new HashMap<String, Object>();
    final var user = ((User) auth.getPrincipal());
    claims.put("fullName", user.getFullName());

    return jwtService.generateToken(claims, (User) auth.getPrincipal());
  }
}
