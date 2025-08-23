package com.vicangel.e_commerce_auction_server_system.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.vicangel.e_commerce_auction_server_system.core.api.AuctionService;
import com.vicangel.e_commerce_auction_server_system.core.error.AuctionException;
import com.vicangel.e_commerce_auction_server_system.core.error.AuctionIdNotFoundException;
import com.vicangel.e_commerce_auction_server_system.core.error.UserException;
import com.vicangel.e_commerce_auction_server_system.core.mappers.AuctionCoreMapper;
import com.vicangel.e_commerce_auction_server_system.core.model.Auction;
import com.vicangel.e_commerce_auction_server_system.core.model.AuctionItem;
import com.vicangel.e_commerce_auction_server_system.core.model.Bid;
import com.vicangel.e_commerce_auction_server_system.core.model.commons.ErrorCodes;
import com.vicangel.e_commerce_auction_server_system.infrastructure.persistence.mysql.repositories.AuctionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
class AuctionServiceImpl implements AuctionService {

  private final AuctionCoreMapper mapper;
  private final AuctionRepository repository;

  @Override
  public long saveAuction(@NonNull final Auction auction) {
    final long id = repository.save(mapper.mapModelToEntity(auction));

    if (id == ErrorCodes.SQL_ERROR.getCode()) {
      throw new AuctionException("Error occurred, inserting auction");
    }

    return id;
  }

  @Override
  public void beginAuction(final long auctionId) {
    final int rowsAffected = repository.beginAuction(auctionId);

    if (rowsAffected == 0) throw new AuctionException("Updating auction failed");
  }

  @Override
  public long addAuctionItem(@NonNull final AuctionItem auctionItem) {
    final long id = repository.insertAuctionItem(mapper.mapAuctionItemModelToEntity(auctionItem));

    if (id == ErrorCodes.SQL_ERROR.getCode()) {
      throw new AuctionException("Error occurred, inserting auction item");
    }

    return id;
  }

  @Override
  public Optional<Auction> findById(final long id, final boolean fetchItems) {
    return repository
      .findById(id, fetchItems)
      .map(mapper::mapEntityToModel);
  }

  @Override
  public List<Auction> findAll(final boolean fetchItems) {
    return repository
      .findAll(fetchItems)
      .stream()
      .map(mapper::mapEntityToModel)
      .toList();
  }

  @Override
  public long bid(@NonNull final Bid bid) {
    final long id = repository.saveBid(mapper.mapBidModelToEntity(bid));

    if (id == ErrorCodes.SQL_ERROR.getCode()) {
      throw new UserException("Error occurred, inserting bid");
    }

    return id;
  }

  @Override
  public boolean deleteAuction(final long id) {

    if (this.findById(id, false).isEmpty()) {
      throw new AuctionIdNotFoundException("Auction id not found to be deleted");
    }

    final int result = repository.deleteById(id);

    return result == 1;
  }

  @Override
  public List<Auction> fetchByCategory(final long categoryId, final boolean fetchItems) {
    return repository
      .findPerCategory(categoryId, fetchItems)
      .stream()
      .map(mapper::mapEntityToModel)
      .toList();
  }
}
