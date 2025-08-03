package com.vicangel.e_commerce_auction_server_system.core.impl;

import org.springframework.stereotype.Service;

import com.vicangel.e_commerce_auction_server_system.core.api.AuctionService;
import com.vicangel.e_commerce_auction_server_system.core.mappers.AuctionCoreMapper;
import com.vicangel.e_commerce_auction_server_system.core.model.Auction;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.ActionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
final class AuctionServiceImpl implements AuctionService {

  private final AuctionCoreMapper auctionCoreMapper;
  private final ActionRepository repository;

  @Override
  public int saveAuction(Auction auction) {

    return repository.save(auctionCoreMapper.mapModelToEntity(auction));
  }

  @Override
  public void beginAuction(long auctionId) {
    repository.beginAuction(auctionId);
  }
}
