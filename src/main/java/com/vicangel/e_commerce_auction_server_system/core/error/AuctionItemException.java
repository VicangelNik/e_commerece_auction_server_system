package com.vicangel.e_commerce_auction_server_system.core.error;

import java.io.Serial;

public final class AuctionItemException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public AuctionItemException(String message, Throwable cause) {
    super(message);
  }
}
