package com.example.demo.controller;

import com.example.demo.entity.AuctionBid;
import com.example.demo.entity.AuctionInvitation;
import com.example.demo.entity.User;
import com.example.demo.service.AuctionService;
import com.example.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebViewController {

    private final AuctionService auctionService;
    private final UserService userService;

    public WebViewController(AuctionService auctionService, UserService userService) {
        this.auctionService = auctionService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String clientHome(Model model, Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login";
        }

        User user = userService.findByEmail(auth.getName()).orElse(null);
        Integer companyId = (user != null && user.getCompany() != null)
                ? user.getCompany().getId() : null;

        List<AuctionInvitation> companyInvitations = companyId != null
                ? auctionService.getCompanyAuctionInvitations(companyId)
                : Collections.emptyList();

        List<AuctionInvitation> activeAuctions = companyId != null
                ? auctionService.getActiveInvitations(companyId)
                : Collections.emptyList();

        List<AuctionInvitation> finishedAuctions = companyId != null
                ? auctionService.getFinishedInvitations(companyId, 5)
                : Collections.emptyList();

        AuctionBid emptyBid = new AuctionBid();
        emptyBid.setBidValue(0.0);

        for (AuctionInvitation inv : activeAuctions) {
            AuctionBid minBid = auctionService.getMinBidValue(inv.getAuction().getId());
            AuctionBid maxBid = auctionService.getLastBidValueForSell(inv.getAuction().getId());
            inv.setMinBidValue(minBid != null ? minBid : emptyBid);
            inv.setMaxBidValue(maxBid != null ? maxBid : emptyBid);
            if (companyId != null && maxBid != null && maxBid.getUser() != null
                    && maxBid.getUser().getCompany() != null) {
                inv.setIsLastBidMine(
                        auctionService.isLastBidMine(companyId,
                                maxBid.getUser().getCompany().getId()) ? 1 : 0);
            } else {
                inv.setIsLastBidMine(2);
            }
        }

        String serverTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Map<String, Object> auctionModel = new HashMap<>();
        auctionModel.put("companyInvitations", companyInvitations);
        auctionModel.put("activeAuction", activeAuctions);
        auctionModel.put("finishedAuction", finishedAuctions);
        auctionModel.put("serverTime", serverTime);
        auctionModel.put("myCompany", companyId);

        model.addAttribute("auction", auctionModel);
        return "client-home";
    }

    @GetMapping("/view/auction/{id}")
    public String showAuctionPage(@PathVariable Integer id, Model model) {
        model.addAttribute("auction", auctionService.getById(id));
        return "auction-details";
    }
}