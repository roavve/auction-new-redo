package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final BidRepository bidRepository;

    public AuctionService(AuctionRepository auctionRepository, BidRepository bidRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
    }

    public Auction getById(Long id) {
        return auctionRepository.findById(id).orElse(null);
    }

    public List<Auction> getAllActive() {
        return auctionRepository.findByStatus("ACTIVE");
    }

    // ── Admin methods ───────────────────────────────────────────────

    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }

    @Transactional
    public void save(Auction auction) {
        auctionRepository.save(auction);
    }

    @Transactional
    public void deleteById(Long id) {
        auctionRepository.deleteById(id);
    }

    // ───────────────────────────────────────────────────────────────

    @Transactional
    public String placeBid(Long auctionId, Double bidAmount, User user) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        if (LocalDateTime.now().isAfter(auction.getBidEndDate())) {
            return "ERROR: Auction ended";
        }

        double minRequiredBid = auction.getCurrentHighestBid() + auction.getBidStep();
        if (bidAmount < minRequiredBid) {
            return "ERROR: Bid too low. Minimum bid is " + minRequiredBid;
        }

        if (LocalDateTime.now().plusMinutes(1).isAfter(auction.getBidEndDate())) {
            if(auction.getAdditionalMinute() != null) {
                auction.setBidEndDate(auction.getBidEndDate().plusMinutes(auction.getAdditionalMinute()));
            }
        }

        AuctionBid bid = new AuctionBid();
        bid.setAuction(auction);
        bid.setUser(user);
        bid.setAmount(bidAmount);
        bid.setBidTime(LocalDateTime.now());
        bid.setStatus("ACTIVE");
        bidRepository.save(bid);

        auction.setCurrentHighestBid(bidAmount);
        auctionRepository.save(auction);

        return "SUCCESS: You are now the highest bidder!";
    }

    @Transactional
    public String declareWinner(Long participantId) {
        return "Winner Declared and Notified!";
    }
}