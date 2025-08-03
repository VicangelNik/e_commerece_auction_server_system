package com.vicangel.e_commerce_auction_server_system;

import java.time.ZoneOffset;
import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
public class ECommerceAuctionServerSystemApplication {

  public static void main(String[] args) {
    SpringApplication.run(ECommerceAuctionServerSystemApplication.class, args);
  }

  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone(ZoneOffset.UTC));
  }
}
