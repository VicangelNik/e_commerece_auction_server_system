package com.vicangel.e_commerce_auction_server_system.core.model.commons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCodes {

  SQL_ERROR(-1);

  @Getter
  private final int code;
}
