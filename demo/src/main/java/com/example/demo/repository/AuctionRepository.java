package com.example.demo.repository;

import com.example.demo.entity.Auction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Integer> {
    List<Auction> findByStatus_Key(String statusKey);
}