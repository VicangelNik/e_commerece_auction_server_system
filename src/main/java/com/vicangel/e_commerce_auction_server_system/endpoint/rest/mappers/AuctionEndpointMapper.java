package com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers;

import java.io.IOException;
import java.util.Base64;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;
import org.springframework.web.multipart.MultipartFile;

import com.vicangel.e_commerce_auction_server_system.core.error.AuctionItemException;
import com.vicangel.e_commerce_auction_server_system.core.model.Auction;
import com.vicangel.e_commerce_auction_server_system.core.model.AuctionItem;
import com.vicangel.e_commerce_auction_server_system.core.model.Bid;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.AuctionItemRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveAuctionRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveBidRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.AuctionResponse;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AuctionEndpointMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "started", ignore = true)
  @Mapping(target = "currentBestBid", ignore = true)
  @Mapping(target = "auctionItems", ignore = true)
  @Mapping(target = "bids", ignore = true)
  Auction mapRequestToModel(SaveAuctionRequest request);

  @Mapping(target = "auctionItems.image", expression = "java( getDecodedImage(auctionItems.image) )")
  AuctionResponse mapModelToResponse(Auction model);

  @Mapping(target = "image", expression = "java( getEncodedImage(image) )")
  @Mapping(target = "name", source = "request.name")
  AuctionItem mapItemRequestToModel(AuctionItemRequest request, MultipartFile image);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "bidSubmittedTime", ignore = true)
  Bid mapBidRequestToModel(SaveBidRequest request);

  default String getEncodedImage(final MultipartFile image) {
    try {
      if (image == null) return null;
      return Base64.getEncoder().encodeToString(image.getBytes());
    } catch (IOException io) {
      throw new AuctionItemException("Auction item's image could not be processed", io);
    }
  }

  default byte[] getDecodedImage(final String image) {
    if (image == null) return null;
    return Base64.getDecoder().decode(image);
  }
}
