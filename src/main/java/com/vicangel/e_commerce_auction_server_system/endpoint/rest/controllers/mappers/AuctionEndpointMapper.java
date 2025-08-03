package com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.vicangel.e_commerce_auction_server_system.core.model.Auction;
import com.vicangel.e_commerce_auction_server_system.endpoint.rest.controllers.dto.request.SaveAuctionRequest;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AuctionEndpointMapper {

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "started", ignore = true)
  @Mapping(target = "currently", ignore = true)
  Auction mapRequestToModel(SaveAuctionRequest request);
}
