package com.example.demo.controller.admin;

import com.example.demo.entity.Auction;
import com.example.demo.service.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminAuctionController {

    private final AuctionService auctionService;

    public AdminAuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping
    public String adminHome(Model model) {
        List<Auction> allAuctions = auctionService.getAll();
        List<Auction> activeAuctions = auctionService.getAllActive();

        model.addAttribute("auctions", allAuctions);
        model.addAttribute("activeCount", activeAuctions.size());
        model.addAttribute("totalCount", allAuctions.size());

        return "admin/home";
    }

    @GetMapping("/auction/new")
    public String newAuctionForm(Model model) {
        model.addAttribute("auction", new Auction());
        return "admin/auction-form";
    }

    @GetMapping("/auction/{id}/edit")
    public String editAuctionForm(@PathVariable Integer id, Model model) {
        model.addAttribute("auction", auctionService.getById(id));
        return "admin/auction-form";
    }

    @PostMapping("/auction/save")
    public String saveNewAuction(
            @RequestParam String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String auctionType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String bidStartDate,
            @RequestParam(required = false) String bidEndDate,
            @RequestParam(required = false) Double startBidValue,
            @RequestParam(required = false) Double maxBidValue,
            @RequestParam(required = false) Double bidStep,
            @RequestParam(required = false) Integer additionalMinute
    ) {
        Auction auction = new Auction();
        auction.setName(name);
        auction.setDesc(desc);
        auction.setBidStartDate(parseDate(bidStartDate));
        auction.setBidEndDate(parseDate(bidEndDate));
        auction.setStartBidValue(startBidValue);
        auction.setMaxBidValue(maxBidValue);
        auction.setBidStep(bidStep);
        auction.setAdditionalMinute(additionalMinute);
        auctionService.save(auction);
        return "redirect:/admin";
    }

    @PostMapping("/auction/{id}/save")
    public String saveExistingAuction(
            @PathVariable Integer id,
            @RequestParam String name,
            @RequestParam(required = false) String desc,
            @RequestParam(required = false) String auctionType,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String bidStartDate,
            @RequestParam(required = false) String bidEndDate,
            @RequestParam(required = false) Double startBidValue,
            @RequestParam(required = false) Double maxBidValue,
            @RequestParam(required = false) Double bidStep,
            @RequestParam(required = false) Integer additionalMinute
    ) {
        Auction auction = auctionService.getById(id);
        if (auction == null) return "redirect:/admin";

        auction.setName(name);
        auction.setDesc(desc);
        auction.setBidStartDate(parseDate(bidStartDate));
        auction.setBidEndDate(parseDate(bidEndDate));
        auction.setStartBidValue(startBidValue);
        auction.setMaxBidValue(maxBidValue);
        auction.setBidStep(bidStep);
        auction.setAdditionalMinute(additionalMinute);
        auctionService.save(auction);
        return "redirect:/admin";
    }

    @PostMapping("/auction/{id}/delete")
    public String deleteAuction(@PathVariable Integer id) {
        auctionService.deleteById(id);
        return "redirect:/admin";
    }

    private Date parseDate(String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").parse(value);
        } catch (Exception e) {
            return null;
        }
    }
}