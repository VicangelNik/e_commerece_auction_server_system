package com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response;

import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import jakarta.annotation.Nonnull;

public record AuctionItemResponse(Long id,
                                  Long auctionId,
                                  String name,
                                  String description,
                                  String location,
                                  Double latitude,
                                  Double longitude,
                                  String country,
                                  Set<Long> categories,
                                  byte[] image,
                                  String imageContentType) {

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) return false;
    AuctionItemResponse that = (AuctionItemResponse) o;
    return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.deepEquals(image, that.image)
           && Objects.equals(auctionId, that.auctionId) && Objects.equals(country, that.country)
           && Objects.equals(location, that.location) && Objects.equals(latitude, that.latitude)
           && Objects.equals(longitude, that.longitude) && Objects.equals(description, that.description)
           && Objects.equals(categories, that.categories) && Objects.equals(imageContentType, that.imageContentType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, auctionId, name, description, location, latitude, longitude, country, categories,
                        Arrays.hashCode(image), imageContentType);
  }

  @Override
  @Nonnull
  public String toString() {
    return "AuctionItemResponse{" +
           "categories=" + categories +
           ", country='" + country + '\'' +
           ", longitude=" + longitude +
           ", latitude=" + latitude +
           ", location='" + location + '\'' +
           ", description='" + description + '\'' +
           ", name='" + name + '\'' +
           ", auctionId=" + auctionId +
           ", id=" + id +
           ", imageContentType='" + imageContentType + '\'' +
           '}';
  }
}