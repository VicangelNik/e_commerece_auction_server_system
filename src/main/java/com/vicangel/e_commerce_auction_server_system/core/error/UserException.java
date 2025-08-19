package com.vicangel.e_commerce_auction_server_system.core.error;

import java.io.Serial;

public final class UserException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public UserException(String message) {
    super(message);
  }

  public UserException(String message, Throwable cause) {
    super(message, cause);
  }
}
