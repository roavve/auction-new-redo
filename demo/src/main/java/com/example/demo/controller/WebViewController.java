package com.example.demo.controller;

import com.example.demo.entity.Auction;
import com.example.demo.service.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class WebViewController {

    private final AuctionService auctionService;

    public WebViewController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/")
    public String clientHome(Model model) {
        List<Auction> all = auctionService.getAll();

        model.addAttribute("activeAuctions", all.stream()
                .filter(a -> "ACTIVE".equals(a.getStatus()))
                .collect(Collectors.toList()));

        model.addAttribute("finishedAuctions", all.stream()
                .filter(a -> !"ACTIVE".equals(a.getStatus()))
                .collect(Collectors.toList()));

        return "client-home";
    }

    @GetMapping("/view/auction/{id}")
    public String showAuctionPage(@PathVariable Long id, Model model) {
        model.addAttribute("auction", auctionService.getById(id));
        return "auction-details";
    }
}