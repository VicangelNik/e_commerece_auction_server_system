package com.vicangel.e_commerce_auction_server_system.core.model;

import java.security.Principal;
import java.time.Instant;
import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;

@Builder
public record User(Long id,
                   Instant created,
                   String password,
                   String name,
                   String surname,
                   String email,
                   String phone,
                   String afm,
                   Integer bidderRating,
                   Integer sellerRating,
                   String location,
                   String country,
                   Set<String> roles,
                   String avatar,
                   boolean accountLocked,
                   boolean enabled) implements UserDetails, Principal {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.roles
      .stream()
      .map(SimpleGrantedAuthority::new)
      .toList();
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return UserDetails.super.isAccountNonExpired();
  }

  @Override
  public boolean isAccountNonLocked() {
    return !accountLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return UserDetails.super.isCredentialsNonExpired();
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public String getName() {
    return email;
  }

  public String getFullName() {
    return name + " " + surname;
  }
}