package com.vicangel.e_commerce_auction_server_system.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.vicangel.e_commerce_auction_server_system.core.api.UserService;
import com.vicangel.e_commerce_auction_server_system.core.mappers.UserCoreMapper;
import com.vicangel.e_commerce_auction_server_system.core.model.User;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
final class UserServiceImpl implements UserService {

  private final UserCoreMapper mapper;
  private final UserRepository repository;

  @Override
  public long insertUser(final @NonNull User user) {
    return repository.insertUser(mapper.mapModelToEntity(user));
  }

  @Override
  public Optional<User> findById(final long id) {
    return repository
      .findById(id)
      .map(mapper::mapEntityToModel);
  }

  @Override
  public List<User> findAll() {
    return repository
      .findAll()
      .stream()
      .map(mapper::mapEntityToModel)
      .toList();
  }
}
