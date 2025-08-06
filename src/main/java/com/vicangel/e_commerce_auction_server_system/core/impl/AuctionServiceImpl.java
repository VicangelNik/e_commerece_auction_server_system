package com.vicangel.e_commerce_auction_server_system.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.vicangel.e_commerce_auction_server_system.core.api.AuctionService;
import com.vicangel.e_commerce_auction_server_system.core.mappers.AuctionCoreMapper;
import com.vicangel.e_commerce_auction_server_system.core.model.Auction;
import com.vicangel.e_commerce_auction_server_system.core.model.AuctionItem;
import com.vicangel.e_commerce_auction_server_system.core.model.Bid;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.AuctionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
final class AuctionServiceImpl implements AuctionService {

  private final AuctionCoreMapper mapper;
  private final AuctionRepository repository;

  @Override
  public long saveAuction(@NonNull final Auction auction) {

    return repository.save(mapper.mapModelToEntity(auction));
  }

  @Override
  public void beginAuction(final long auctionId) {
    repository.beginAuction(auctionId);
  }

  @Override
  public long addAuctionItem(@NonNull final AuctionItem auctionItem) {
    return repository.insertAuctionItem(mapper.mapAuctionItemModelToEntity(auctionItem));
  }

  @Override
  public Optional<Auction> findById(Long id) {
    return repository
      .findById(id)
      .map(mapper::mapEntityToModel);
  }

  @Override
  public List<Auction> findAll() {
    return repository
      .findAll()
      .stream()
      .map(mapper::mapEntityToModel)
      .toList();
  }

  @Override
  public long bid(@NonNull final Bid bid) {
    return repository.saveBid(mapper.mapBidModelToEntity(bid));
  }
}
