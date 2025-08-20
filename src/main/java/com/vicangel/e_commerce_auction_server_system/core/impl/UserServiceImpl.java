package com.vicangel.e_commerce_auction_server_system.core.impl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.vicangel.e_commerce_auction_server_system.core.api.UserService;
import com.vicangel.e_commerce_auction_server_system.core.error.UserException;
import com.vicangel.e_commerce_auction_server_system.core.error.UserIdNotFoundException;
import com.vicangel.e_commerce_auction_server_system.core.mappers.UserCoreMapper;
import com.vicangel.e_commerce_auction_server_system.core.model.User;
import com.vicangel.e_commerce_auction_server_system.core.model.commons.ErrorCodes;
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
    final long id = repository.insertUser(mapper.mapModelToEntity(user));

    if (id == ErrorCodes.SQL_ERROR.getCode()) {
      throw new UserException("Error occurred, inserting user item");
    }

    return id;
  }

  @Override
  public Optional<User> findById(final long id, final boolean fetchAvatar) {
    return repository
      .findById(id, fetchAvatar)
      .map(mapper::mapEntityToModel);
  }

  @Override
  public List<User> findAll(final boolean fetchAvatar) {
    return repository
      .findAll(fetchAvatar)
      .stream()
      .map(mapper::mapEntityToModel)
      .toList();
  }

  @Override
  public void updateUser(final long id, final User updatedUser) {

    final User userToUpdate = repository.findById(id, true)
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
        .roles(getOnlyNewRoles(updatedUser.roles(), existingUser.roles()))
        .avatar(updatedUser.avatar() != null ? updatedUser.avatar() : existingUser.avatar())
        .build())
      .orElseThrow(() -> new UserIdNotFoundException("User not found with id: " + id));

    final int rowsAffected = repository.updateUser(mapper.mapModelToEntity(userToUpdate));

    if (rowsAffected == 0) throw new UserException("Updating user failed with id: " + id);
  }

  private static Set<String> getOnlyNewRoles(final Set<String> rolesNew, final Set<String> rolesExisting) {

    if (rolesExisting == null || rolesExisting.isEmpty()) {
      throw new UserException("User entity is on an illegal state, roles cannot be empty.");
    }
    if (rolesNew == null || rolesNew.isEmpty()) return Set.of();

    rolesNew.removeAll(rolesExisting);

    return rolesNew;
  }
}
