package com.example.demo.controller.admin;

import com.example.demo.entity.Auction;
import com.example.demo.service.AuctionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/auction/{id}/delete")
    public String deleteAuction(@PathVariable Integer id) {
        auctionService.deleteById(id);
        return "redirect:/admin";
    }
}