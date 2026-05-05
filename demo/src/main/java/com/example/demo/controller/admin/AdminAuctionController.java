package com.example.demo.controller.admin;

import com.example.demo.entity.Auction;
import com.example.demo.service.AuctionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminAuctionController {

    private final AuctionService auctionService;

    public AdminAuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    /** Admin dashboard — lists all auctions */
    @GetMapping
    public String adminHome(Model model) {
        List<Auction> allAuctions = auctionService.getAll();
        List<Auction> activeAuctions = auctionService.getAllActive();

        model.addAttribute("auctions", allAuctions);
        model.addAttribute("activeCount", activeAuctions.size());
        model.addAttribute("totalCount", allAuctions.size());

        return "admin/home";
    }

    /** Show blank creation form */
    @GetMapping("/auction/new")
    public String newAuctionForm(Model model) {
        model.addAttribute("auction", new Auction());
        return "admin/auction-form";
    }

    /** Show pre-filled edit form */
    @GetMapping("/auction/{id}/edit")
    public String editAuctionForm(@PathVariable Long id, Model model) {
        model.addAttribute("auction", auctionService.getById(id));
        return "admin/auction-form";
    }

    /** Save a brand new auction */
    @PostMapping("/auction/save")
    public String saveNewAuction(
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam String auctionType,
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidStartDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidEndDate,
            @RequestParam Double startBidValue,
            @RequestParam(required = false) Double maxBidValue,
            @RequestParam Double bidStep,
            @RequestParam(required = false) Integer additionalMinute) {

        Auction auction = new Auction();
        auction.setName(name);
        auction.setDescription(description);
        auction.setAuctionType(auctionType);
        auction.setStatus(status);
        auction.setBidStartDate(bidStartDate);
        auction.setBidEndDate(bidEndDate);
        auction.setStartBidValue(startBidValue);
        auction.setCurrentHighestBid(startBidValue);
        auction.setMaxBidValue(maxBidValue);
        auction.setBidStep(bidStep);
        auction.setAdditionalMinute(additionalMinute);

        auctionService.save(auction);
        return "redirect:/admin";
    }

    /** Save edits to an existing auction */
    @PostMapping("/auction/{id}/save")
    public String saveExistingAuction(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam(required = false) String description,
            @RequestParam String auctionType,
            @RequestParam String status,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidStartDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime bidEndDate,
            @RequestParam Double startBidValue,
            @RequestParam(required = false) Double maxBidValue,
            @RequestParam Double bidStep,
            @RequestParam(required = false) Integer additionalMinute) {

        Auction auction = auctionService.getById(id);
        auction.setName(name);
        auction.setDescription(description);
        auction.setAuctionType(auctionType);
        auction.setStatus(status);
        auction.setBidStartDate(bidStartDate);
        auction.setBidEndDate(bidEndDate);
        auction.setStartBidValue(startBidValue);
        auction.setMaxBidValue(maxBidValue);
        auction.setBidStep(bidStep);
        auction.setAdditionalMinute(additionalMinute);

        auctionService.save(auction);
        return "redirect:/admin";
    }

    /** Delete an auction */
    @PostMapping("/auction/{id}/delete")
    public String deleteAuction(@PathVariable Long id) {
        auctionService.deleteById(id);
        return "redirect:/admin";
    }
}