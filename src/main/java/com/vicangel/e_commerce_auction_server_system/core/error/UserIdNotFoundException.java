package com.vicangel.e_commerce_auction_server_system.core.error;

import java.io.Serial;

public final class UserIdNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public UserIdNotFoundException(String message) {
    super(message);
  }
}
