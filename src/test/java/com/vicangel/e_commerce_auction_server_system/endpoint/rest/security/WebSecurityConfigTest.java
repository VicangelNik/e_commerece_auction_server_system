package com.vicangel.e_commerce_auction_server_system.endpoint.rest.security;

import java.util.Base64;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.jsonwebtoken.Jwts;

final class WebSecurityConfigTest {

  @Test
  void generateSecretKey() {
    final SecretKey key = Jwts.SIG.HS512.key().build();

    final String base64Key = Base64.getEncoder().encodeToString(key.getEncoded());

    System.out.println("--- YOUR NEW SECRET KEY ---");
    System.out.println(base64Key);
    System.out.println("---------------------------");

    Assertions.assertNotNull(base64Key);
  }
}