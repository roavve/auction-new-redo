package com.example.demo.controller;

import com.example.demo.entity.*;
import com.example.demo.service.AuctionService;
import com.example.demo.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/auction")
public class ClientAuctionController {

    private final AuctionService auctionService;
    private final UserService userService;

    public ClientAuctionController(AuctionService auctionService, UserService userService) {
        this.auctionService = auctionService;
        this.userService = userService;
    }

    // ── Helper ────────────────────────────────────────────────────────────────

    private User getCurrentUser(Authentication auth) {
        return userService.findByEmail(auth.getName()).orElse(null);
    }

    // ── New invitation page ───────────────────────────────────────────────────

    @GetMapping("/new_invitation")
    public String newInvitation(@RequestParam("recordKey") String recordKey,
                                Authentication auth, Model model) {
        AuctionInvitation invitation = auctionService.getAuctionInvitation(recordKey);
        if (invitation == null) return "redirect:/";

        List<AuctionRevisionFile> revisionFiles = auctionService.getRevisionFiles(invitation.getAuction().getId());

        Map<String, Object> info = new HashMap<>();
        info.put("auctionInvitation", invitation);
        info.put("revisionFiles", revisionFiles);
        info.put("loginUser", auth.getName());

        model.addAttribute("auctionInfo", info);
        return "auction/new-invitation";
    }

    // ── Accept / Reject invitation ────────────────────────────────────────────

    @PostMapping("/auction_accept")
    public String auctionAccept(@RequestParam("recordKey") String recordKey,
                                Authentication auth) {
        User user = getCurrentUser(auth);
        if (user != null) {
            auctionService.agreeInvitation(recordKey, user.getId());
        }
        return "redirect:/";
    }

    @PostMapping("/auction_reject")
    public String auctionReject(@RequestParam("recordKey") String recordKey,
                                Authentication auth) {
        User user = getCurrentUser(auth);
        if (user != null) {
            auctionService.rejectInvitation(recordKey, user.getId());
        }
        return "redirect:/";
    }

    // ── Active auction detail page ────────────────────────────────────────────

    @GetMapping("/active_invitation")
    public String activeInvitation(@RequestParam("recordKey") String recordKey,
                                   Authentication auth, Model model) {
        AuctionInvitation invitation = auctionService.getAuctionInvitation(recordKey);
        if (invitation == null) return "redirect:/";

        User currentUser = getCurrentUser(auth);
        Integer companyId = (currentUser != null && currentUser.getCompany() != null)
                ? currentUser.getCompany().getId() : null;

        Auction auction = invitation.getAuction();

        List<AuctionComment> comments = auctionService.getAuctionComments(auction.getId());
        List<AuctionBid> auctionBids = companyId != null
                ? auctionService.getAuctionBids(auction.getId(), companyId)
                : Collections.emptyList();

        AuctionBid minBidValue = auctionService.getMinBidValue(auction.getId());
        List<AuctionRevision> revisions = auctionService.getRevisionFilesByRevision(auction.getId());
        Long countFirstPeriodBid = companyId != null
                ? auctionService.countFirstPeriodBids(companyId, auction.getId()) : 0L;

        String serverTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        String auctionStartDateStr = auction.getAuctionStartDate() != null
                ? new SimpleDateFormat("yyyy-MM-dd").format(auction.getAuctionStartDate())
                + " " + auction.getStartTime() : "";
        String endTimeStr = auction.getBidEndDate() != null
                ? new SimpleDateFormat("yyyy-MM-dd").format(auction.getBidEndDate())
                + " " + auction.getBidEndTime() + ":00" : "";

        int isAuctionLive = auctionService.isAuctionLive(
                auctionStartDateStr, auction.getLastBidDate(), endTimeStr,
                auction.getAdditionalMinute() != null ? auction.getAdditionalMinute() : 0);

        Map<String, Object> info = new HashMap<>();
        info.put("auctionInvitation", invitation);
        info.put("revisions", revisions);
        info.put("minBidValue", minBidValue);
        info.put("comments", comments);
        info.put("serverTime", serverTime);
        info.put("auctionBids", auctionBids);
        info.put("countFirstPeriodBid", countFirstPeriodBid);
        info.put("loginUser", auth.getName());
        info.put("loginUserId", currentUser != null ? currentUser.getId() : null);
        info.put("auctionFinalShowAuctionBidBtn", isAuctionLive);

        model.addAttribute("auctionInfo", info);
        return "auction/active-invitation";
    }

    // ── Finished auction detail page ──────────────────────────────────────────

    @GetMapping("/finished_auction")
    public String finishedAuction(@RequestParam("recordKey") String recordKey,
                                  Authentication auth, Model model) {
        AuctionInvitation invitation = auctionService.getAuctionInvitation(recordKey);
        if (invitation == null) return "redirect:/";

        User currentUser = getCurrentUser(auth);
        Integer companyId = (currentUser != null && currentUser.getCompany() != null)
                ? currentUser.getCompany().getId() : null;

        Auction auction = invitation.getAuction();

        List<AuctionComment> comments = auctionService.getAuctionCommentsByUser(
                auction.getId(), auth.getName());
        List<AuctionBid> auctionBids = companyId != null
                ? auctionService.getAuctionBids(auction.getId(), companyId)
                : Collections.emptyList();

        AuctionBid minBidValue = auctionService.getMinBidValue(auction.getId());
        List<AuctionRevision> revisions = auctionService.getRevisionFilesByRevision(auction.getId());
        Long countFirstPeriodBid = companyId != null
                ? auctionService.countFirstPeriodBids(companyId, auction.getId()) : 0L;

        Map<String, Object> info = new HashMap<>();
        info.put("auctionInvitation", invitation);
        info.put("revisions", revisions);
        info.put("minBidValue", minBidValue);
        info.put("comments", comments);
        info.put("auctionBids", auctionBids);
        info.put("countFirstPeriodBid", countFirstPeriodBid);
        info.put("loginUser", auth.getName());
        info.put("loginUserId", currentUser != null ? currentUser.getId() : null);

        model.addAttribute("auctionInfo", info);
        return "auction/finished-auction";
    }

    // ── Finished auctions list ────────────────────────────────────────────────

    @GetMapping("/finished_auctions")
    public String finishedAuctions(Authentication auth, Model model) {
        User currentUser = getCurrentUser(auth);
        Integer companyId = (currentUser != null && currentUser.getCompany() != null)
                ? currentUser.getCompany().getId() : null;

        List<AuctionInvitation> finished = companyId != null
                ? auctionService.getFinishedInvitations(companyId)
                : Collections.emptyList();

        model.addAttribute("auction", Map.of("finishedAuction", finished));
        return "auction/finished-auctions-list";
    }

    // ── Add comment ───────────────────────────────────────────────────────────

    @PostMapping("/add_comment")
    public String addComment(@RequestParam("auctionId") Integer auctionId,
                             @RequestParam("comment") String commentText,
                             @RequestParam("invitationKey") String invitationKey,
                             Authentication auth) {
        if (commentText == null || commentText.trim().isEmpty()) {
            return "redirect:/auction/active_invitation?recordKey=" + invitationKey;
        }

        User currentUser = getCurrentUser(auth);
        Auction auction = auctionService.getById(auctionId);

        AuctionComment comment = new AuctionComment();
        comment.setAuction(auction);
        comment.setUser(currentUser);
        comment.setCommText(commentText.trim());

        int res = auctionService.addComment(comment);
        if (res > 0) {
            return "redirect:/auction/active_invitation?recordKey=" + invitationKey;
        }
        return "redirect:/";
    }

    // ── Place bid ─────────────────────────────────────────────────────────────

    @PostMapping("/update_bid")
    public String updateBid(@RequestParam("recordKey") Integer auctionId,
                            @RequestParam("invitationKey") String invitationKey,
                            @RequestParam("bidValue") Double bidValue,
                            @RequestParam(value = "bidPeriod", defaultValue = "2") Integer bidPeriod,
                            Authentication auth) {
        User currentUser = getCurrentUser(auth);
        Auction auction = auctionService.getById(auctionId);

        AuctionBid bid = new AuctionBid();
        bid.setBidValue(bidValue);
        bid.setUser(currentUser);
        bid.setBidPeriod(bidPeriod);

        int res;
        if (!Boolean.TRUE.equals(auction.getShowLastBid())) {
            bid.setBidPeriod(1);
            res = auctionService.updateBidValueWithout(bid, auction);
        } else {
            res = auctionService.updateBidValue(bid, auction);
        }

        return "redirect:/auction/active_invitation?recordKey=" + invitationKey;
    }

    @PostMapping("/update_sell_bid")
    public String updateSellBid(@RequestParam("recordKey") Integer auctionId,
                                @RequestParam("invitationKey") String invitationKey,
                                @RequestParam("bidValue") Double bidValue,
                                Authentication auth) {
        User currentUser = getCurrentUser(auth);
        Auction auction = auctionService.getById(auctionId);

        AuctionBid bid = new AuctionBid();
        bid.setBidValue(bidValue);
        bid.setUser(currentUser);

        int res;
        if (!Boolean.TRUE.equals(auction.getShowLastBid())) {
            bid.setBidPeriod(1);
            res = auctionService.updateBidValueWithout(bid, auction);
        } else {
            bid.setBidPeriod(2);
            res = auctionService.updateBidValueSell(bid, auction);
        }

        return "redirect:/auction/active_invitation?recordKey=" + invitationKey;
    }
}