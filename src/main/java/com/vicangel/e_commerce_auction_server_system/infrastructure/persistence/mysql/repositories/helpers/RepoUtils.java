package com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.helpers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

import org.springframework.lang.NonNull;

public final class RepoUtils {

  private RepoUtils() {
    throw new IllegalArgumentException("Utility class");
  }

  static Instant toInstant(final @NonNull ResultSet rs, final @NonNull String column) throws SQLException {
    final var ts = rs.getTimestamp(column);
    return ts != null ? ts.toInstant() : null;
  }
}
