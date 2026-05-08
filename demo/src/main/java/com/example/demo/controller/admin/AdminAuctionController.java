package com.example.demo.controller.admin;

import com.example.demo.entity.Auction;
import com.example.demo.repository.DictionaryItemRepository;
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
    private final DictionaryItemRepository diRepo;

    public AdminAuctionController(AuctionService auctionService,
                                  DictionaryItemRepository diRepo) {
        this.auctionService = auctionService;
        this.diRepo = diRepo;
    }

    @GetMapping
    public String adminHome(Model model) {
        List<Auction> allAuctions = auctionService.getAll();

        long activeCount = allAuctions.stream()
                .filter(a -> a.getStatus() != null
                        && "key.auctionStatus.active".equals(a.getStatus().getKey()))
                .count();
        long finishedCount = allAuctions.stream()
                .filter(a -> a.getStatus() != null
                        && "key.auctionStatus.completed".equals(a.getStatus().getKey()))
                .count();

        model.addAttribute("auctions", allAuctions);
        model.addAttribute("activeCount", activeCount);
        model.addAttribute("finishedCount", finishedCount);
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
        applyFormFields(auction, name, desc, auctionType, status,
                bidStartDate, bidEndDate, startBidValue, maxBidValue, bidStep, additionalMinute);
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
        applyFormFields(auction, name, desc, auctionType, status,
                bidStartDate, bidEndDate, startBidValue, maxBidValue, bidStep, additionalMinute);
        auctionService.save(auction);
        return "redirect:/admin";
    }

    @PostMapping("/auction/{id}/delete")
    public String deleteAuction(@PathVariable Integer id) {
        auctionService.deleteById(id);
        return "redirect:/admin";
    }

    private void applyFormFields(Auction auction, String name, String desc,
                                 String auctionType, String status, String bidStartDate, String bidEndDate,
                                 Double startBidValue, Double maxBidValue, Double bidStep, Integer additionalMinute) {
        auction.setName(name);
        auction.setDesc(desc);
        auction.setBidStartDate(parseDate(bidStartDate));
        auction.setBidEndDate(parseDate(bidEndDate));
        auction.setStartBidValue(startBidValue);
        auction.setMaxBidValue(maxBidValue);
        auction.setBidStep(bidStep);
        auction.setAdditionalMinute(additionalMinute);

        if (auctionType != null) {
            String key = "BUY".equals(auctionType) ? "key.auctionType.buy" : "key.auctionType.sell";
            diRepo.findByKey(key).ifPresent(auction::setAuctionType);
        }
        if (status != null) {
            String key = "ACTIVE".equals(status)
                    ? "key.auctionStatus.active" : "key.auctionStatus.completed";
            diRepo.findByKey(key).ifPresent(auction::setStatus);
        }
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