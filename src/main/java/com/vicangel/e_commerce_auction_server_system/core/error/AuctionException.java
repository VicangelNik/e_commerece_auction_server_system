package com.vicangel.e_commerce_auction_server_system.core.error;

import java.io.Serial;

public final class AuctionException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public AuctionException(String message) {
    super(message);
  }
}
