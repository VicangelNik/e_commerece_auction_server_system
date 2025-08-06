package com.vicangel.e_commerce_auction_server_system.core.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import com.vicangel.e_commerce_auction_server_system.core.model.Auction;
import com.vicangel.e_commerce_auction_server_system.core.model.AuctionItem;
import com.vicangel.e_commerce_auction_server_system.core.model.Bid;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.AuctionItemEntity;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.entities.BidEntity;

@Mapper(componentModel = ComponentModel.SPRING)
public interface AuctionCoreMapper {

  @Mapping(source = "created", target = "created", defaultExpression = "java(java.time.Instant.now())")
  AuctionEntity mapModelToEntity(Auction auction);

  Auction mapEntityToModel(AuctionEntity entity);

  AuctionItemEntity mapAuctionItemModelToEntity(AuctionItem auctionItem);

  BidEntity mapBidModelToEntity(Bid bid);
}

