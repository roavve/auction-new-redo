package com.example.demo.repository;

import com.example.demo.entity.AuctionBid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionBidRepository extends JpaRepository<AuctionBid, Integer> {

    @Query("SELECT b FROM AuctionBid b WHERE b.auction.id = :auctionId " +
            "AND b.status.key = 'key.bid.active' " +
            "ORDER BY b.bidValue ASC")
    List<AuctionBid> findMinBid(@Param("auctionId") Integer auctionId);

    @Query("SELECT b FROM AuctionBid b WHERE b.auction.id = :auctionId " +
            "AND b.status.key = 'key.bid.active' " +
            "ORDER BY b.bidValue DESC")
    List<AuctionBid> findMaxBid(@Param("auctionId") Integer auctionId);

    @Query("SELECT b FROM AuctionBid b WHERE b.auction.id = :auctionId " +
            "AND b.user.company.id = :companyId " +
            "AND b.status.key = 'key.bid.active' " +
            "ORDER BY b.bidDate DESC")
    List<AuctionBid> findBidsByAuctionAndCompany(@Param("auctionId") Integer auctionId,
                                                 @Param("companyId") Integer companyId);

    @Query("SELECT b FROM AuctionBid b WHERE b.auction.id = :auctionId " +
            "AND b.user.id = :userId " +
            "AND b.status.key = 'key.bid.active' " +
            "ORDER BY b.bidValue ASC")
    List<AuctionBid> findMyMinBid(@Param("auctionId") Integer auctionId,
                                  @Param("userId") Integer userId);

    @Query("SELECT b FROM AuctionBid b WHERE b.auction.id = :auctionId " +
            "AND b.user.id = :userId " +
            "AND b.status.key = 'key.bid.active' " +
            "ORDER BY b.bidValue DESC")
    List<AuctionBid> findMyMaxBid(@Param("auctionId") Integer auctionId,
                                  @Param("userId") Integer userId);

    @Query("SELECT COUNT(b) FROM AuctionBid b WHERE b.auction.id = :auctionId " +
            "AND b.user.company.id = :companyId " +
            "AND b.bidPeriod = 1 " +
            "AND b.status.key = 'key.bid.active'")
    Long countFirstPeriodBids(@Param("auctionId") Integer auctionId,
                              @Param("companyId") Integer companyId);
}