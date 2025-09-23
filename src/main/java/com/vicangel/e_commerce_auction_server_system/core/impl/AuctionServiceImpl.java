package com.vicangel.e_commerce_auction_server_system.core.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import com.vicangel.e_commerce_auction_server_system.core.api.AuctionService;
import com.vicangel.e_commerce_auction_server_system.core.error.AuctionException;
import com.vicangel.e_commerce_auction_server_system.core.error.AuctionIdNotFoundException;
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

  /**
   * Bid can only be made if the auction has started and the bid amount is greater than the current bid or the bid amount
   * is greater or equal to the first bid.
   */
  @Override
  public void bid(@NonNull final Bid bid) {

    final var auction = this.findById(bid.auctionId(), false)
      .orElseThrow(() -> new AuctionIdNotFoundException("Auction id not found to bid"));

    if (auction.started() == null) throw new AuctionException("Auction has not started, cannot bid");
    if (auction.currentBestBid() != null && auction.currentBestBid() > 0) {
      if (bid.amount() < auction.currentBestBid()) {
        throw new AuctionException("Bid amount must be greater than current bid");
      }
    } else {
      if (bid.amount() <= auction.firstBid()) {
        throw new AuctionException("Bid amount must be greater than or equal to first bid");
      }
    }

    final long id = repository.saveBid(mapper.mapBidModelToEntity(bid));

    if (id == ErrorCodes.SQL_ERROR.getCode()) {
      throw new AuctionException("Error occurred, inserting bid");
    }
  }

  /**
   * Auction cannot be deleted if it has started or has bids.
   */
  @Override
  public boolean deleteAuction(final long id) {

    final var auction = this.findById(id, false)
      .orElseThrow(() -> new AuctionIdNotFoundException("Auction id not found to be deleted"));

    if (auction.started() != null) throw new AuctionException("Auction has started, cannot be deleted");
    if (!auction.bids().isEmpty()) throw new AuctionException("Auction has bids, cannot be deleted");

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
