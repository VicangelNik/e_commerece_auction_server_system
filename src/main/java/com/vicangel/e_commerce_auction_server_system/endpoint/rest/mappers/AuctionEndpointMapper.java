package com.vicangel.e_commerce_auction_server_system.endpoint.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.vicangel.e_commerce_auction_server_system.core.model.Auction;
import com.vicangel.e_commerce_auction_server_system.core.model.AuctionItem;
import com.vicangel.e_commerce_auction_server_system.core.model.Bid;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.AuctionItemDTO;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveAuctionRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.request.SaveBidRequest;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.dto.response.AuctionResponse;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AuctionEndpointMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "started", ignore = true)
  @Mapping(target = "currently", ignore = true)
  Auction mapRequestToModel(SaveAuctionRequest request);

  AuctionResponse mapModelToResponse(Auction model);

  AuctionItem mapAuctionRequestToModel(AuctionItemDTO request);

  @Mapping(target = "id", ignore = true)
  Bid mapBidRequestToModel(SaveBidRequest request);
}
