package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.AuctionService;
import org.springframework.web.bind.annotation.*;
import com.example.demo.entity.Auction;
import java.util.List;

@RestController
@RequestMapping("/api/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }
    @GetMapping("/{id}")
    public Auction getAuctionDetails(@PathVariable Long id) {
        return auctionService.getById(id);
    }
    @GetMapping("/active")
    public List<Auction> getActiveAuctions() {
        return auctionService.getAllActive();
    }
    @GetMapping("/{id}/bid")
    public void placeBid(
            @PathVariable Long id,
            @RequestParam Double amount,
            @RequestParam Long userId,
            jakarta.servlet.http.HttpServletResponse response) throws java.io.IOException {

        User tester = new User();
        tester.setId(userId);

        auctionService.placeBid(id, amount, tester);

        response.sendRedirect("/view/auction/" + id);
    }
}