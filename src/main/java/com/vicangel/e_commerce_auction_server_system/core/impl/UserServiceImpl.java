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

  @Override
  public void updateUser(final long id, final User updatedUser) {

    final User userToUpdate = repository.findById(id)
      .map(mapper::mapEntityToModel)
      .map(existingUser -> User.builder().id(existingUser.id())
        .created(existingUser.created())
        .username(updatedUser.username() != null ? updatedUser.username() : existingUser.username())
        .password(updatedUser.password() != null ? updatedUser.password() : existingUser.password())
        .name(updatedUser.name() != null ? updatedUser.name() : existingUser.name())
        .surname(updatedUser.surname() != null ? updatedUser.surname() : existingUser.surname())
        .email(updatedUser.email() != null ? updatedUser.email() : existingUser.email())
        .phone(updatedUser.phone() != null ? updatedUser.phone() : existingUser.phone())
        .afm(updatedUser.afm() != null ? updatedUser.afm() : existingUser.afm())
        .bidderRating(updatedUser.bidderRating() != null ? updatedUser.bidderRating() : existingUser.bidderRating())
        .sellerRating(updatedUser.sellerRating() != null ? updatedUser.sellerRating() : existingUser.sellerRating())
        .location(updatedUser.location() != null ? updatedUser.location() : existingUser.location())
        .country(updatedUser.country() != null ? updatedUser.country() : existingUser.country())
        .build())
      .orElseThrow(() -> new IllegalArgumentException("User not found"));

    final int rowsAffected = repository.updateUser(mapper.mapModelToEntity(userToUpdate));

    if (rowsAffected == 0) throw new IllegalArgumentException("Update user failed with id: " + id);
  }
}
