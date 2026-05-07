package com.example.demo.controller;

import com.example.demo.entity.Auction;
import com.example.demo.service.AuctionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/{id}")
    public Auction getAuctionDetails(@PathVariable Integer id) {
        return auctionService.getById(id);
    }

    @GetMapping("/active")
    public List<Auction> getActiveAuctions() {
        return auctionService.getAllActive();
    }
}