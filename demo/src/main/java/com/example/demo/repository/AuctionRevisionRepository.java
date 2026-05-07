package com.example.demo.repository;

import com.example.demo.entity.AuctionRevision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRevisionRepository extends JpaRepository<AuctionRevision, Integer> {
    List<AuctionRevision> findByAuction_IdOrderByRevisionNumDesc(Integer auctionId);
}