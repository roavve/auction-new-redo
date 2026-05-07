package com.example.demo.service;

import com.example.demo.entity.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final AuctionBidRepository bidRepository;
    private final AuctionInvitationRepository invitationRepository;
    private final DictionaryItemRepository dictionaryItemRepository;
    private final AuctionCommentRepository commentRepository;
    private final AuctionRevisionRepository revisionRepository;

    public AuctionService(AuctionRepository auctionRepository,
                          AuctionBidRepository bidRepository,
                          AuctionInvitationRepository invitationRepository,
                          DictionaryItemRepository dictionaryItemRepository,
                          AuctionCommentRepository commentRepository,
                          AuctionRevisionRepository revisionRepository) {
        this.auctionRepository = auctionRepository;
        this.bidRepository = bidRepository;
        this.invitationRepository = invitationRepository;
        this.dictionaryItemRepository = dictionaryItemRepository;
        this.commentRepository = commentRepository;
        this.revisionRepository = revisionRepository;
    }

    // ── Basic CRUD ────────────────────────────────────────────────────────────

    public Auction getById(Integer id) {
        return auctionRepository.findById(id).orElse(null);
    }

    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }

    public List<Auction> getAllActive() {
        return auctionRepository.findByStatus_Key("key.auctionStatus.active");
    }

    @Transactional
    public void save(Auction auction) {
        auctionRepository.save(auction);
    }

    @Transactional
    public void deleteById(Integer id) {
        auctionRepository.deleteById(id);
    }

    // ── Invitation queries ────────────────────────────────────────────────────

    public AuctionInvitation getAuctionInvitation(String recordKey) {
        return invitationRepository.findByRecordKey(recordKey).orElse(null);
    }

    public List<AuctionInvitation> getCompanyAuctionInvitations(Integer companyId) {
        return invitationRepository.findNewInvitationsByCompany(companyId);
    }

    public List<AuctionInvitation> getActiveInvitations(Integer companyId) {
        return invitationRepository.findActiveInvitationsByCompany(companyId);
    }

    public List<AuctionInvitation> getFinishedInvitations(Integer companyId) {
        return invitationRepository.findFinishedInvitationsByCompany(companyId);
    }

    public List<AuctionInvitation> getFinishedInvitations(Integer companyId, int limit) {
        List<AuctionInvitation> all = invitationRepository.findFinishedInvitationsByCompany(companyId);
        return all.size() > limit ? all.subList(0, limit) : all;
    }

    public Long countNewInvitations(Integer companyId) {
        return invitationRepository.countNewInvitations(companyId);
    }

    @Transactional
    public void rejectInvitation(String recordKey, Integer compUserId) {
        AuctionInvitation inv = invitationRepository.findByRecordKey(recordKey).orElseThrow();
        DictionaryItem rejected = dictionaryItemRepository
                .findByKey("key.auctionInvitation.rejected").orElseThrow();
        User user = new User();
        user.setId(compUserId);
        inv.setStatus(rejected);
        inv.setDateRejected(new Date());
        inv.setCompanyUser(user);
        invitationRepository.save(inv);
    }

    @Transactional
    public void agreeInvitation(String recordKey, Integer compUserId) {
        AuctionInvitation inv = invitationRepository.findByRecordKey(recordKey).orElseThrow();
        DictionaryItem approved = dictionaryItemRepository
                .findByKey("key.bid.status.active").orElseThrow();
        User user = new User();
        user.setId(compUserId);
        inv.setStatus(approved);
        inv.setDateAccepted(new Date());
        inv.setCompanyUser(user);
        invitationRepository.save(inv);
    }

    // ── Bid queries ───────────────────────────────────────────────────────────

    public AuctionBid getMinBidValue(Integer auctionId) {
        List<AuctionBid> bids = bidRepository.findMinBid(auctionId);
        return bids.isEmpty() ? null : bids.get(0);
    }

    public AuctionBid getLastBidValueForSell(Integer auctionId) {
        List<AuctionBid> bids = bidRepository.findMaxBid(auctionId);
        return bids.isEmpty() ? null : bids.get(0);
    }

    public AuctionBid getMyMinBidValue(Integer auctionId, Integer userId) {
        List<AuctionBid> bids = bidRepository.findMyMinBid(auctionId, userId);
        return bids.isEmpty() ? null : bids.get(0);
    }

    public AuctionBid getMyLastBidValueForSell(Integer auctionId, Integer userId) {
        List<AuctionBid> bids = bidRepository.findMyMaxBid(auctionId, userId);
        return bids.isEmpty() ? null : bids.get(0);
    }

    public List<AuctionBid> getAuctionBids(Integer auctionId, Integer companyId) {
        return bidRepository.findBidsByAuctionAndCompany(auctionId, companyId);
    }

    public Long countFirstPeriodBids(Integer companyId, Integer auctionId) {
        return bidRepository.countFirstPeriodBids(auctionId, companyId);
    }

    public boolean isLastBidMine(Integer myCompanyId, Integer bidderCompanyId) {
        return myCompanyId != null && myCompanyId.equals(bidderCompanyId);
    }

    // ── Bid submission ────────────────────────────────────────────────────────

    @Transactional
    public int updateBidValue(AuctionBid auctionBid, Auction auction) {
        AuctionBid currentMin = getMinBidValue(auction.getId());
        if (currentMin != null) {
            if (auctionBid.getBidValue() >= currentMin.getBidValue()) return -1;
            double remainder = (currentMin.getBidValue() - auctionBid.getBidValue()) % auction.getBidStep();
            if (remainder != 0) return -2;
        }
        if (auction.getStartBidValue() != null && auctionBid.getBidValue() > auction.getStartBidValue()) return -5;
        return saveBid(auctionBid, auction);
    }

    @Transactional
    public int updateBidValueSell(AuctionBid auctionBid, Auction auction) {
        AuctionBid currentMax = getLastBidValueForSell(auction.getId());
        if (currentMax != null) {
            if (auctionBid.getBidValue() <= currentMax.getBidValue()) return -7;
            double remainder = (auctionBid.getBidValue() - currentMax.getBidValue()) % auction.getBidStep();
            if (remainder != 0) return -8;
        }
        if (auction.getStartBidValue() != null && auctionBid.getBidValue() < auction.getStartBidValue()) return -9;
        return saveBid(auctionBid, auction);
    }

    @Transactional
    public int updateBidValueWithout(AuctionBid auctionBid, Auction auction) {
        if (auction.getStartBidValue() != null && auctionBid.getBidValue() > auction.getStartBidValue()) return -5;
        if (auction.getStartBidValue() != null
                && auctionBid.getBidValue() < auction.getStartBidValue()
                && "key.auctionType.sell".equals(auction.getAuctionType().getKey())) return -6;
        return saveBid(auctionBid, auction);
    }

    private int saveBid(AuctionBid auctionBid, Auction auction) {
        DictionaryItem activeStatus = dictionaryItemRepository
                .findByKey("key.bid.active").orElseThrow();
        auctionBid.setRecordKey(UUID.randomUUID().toString());
        auctionBid.setBidDate(new Date());
        auctionBid.setStatus(activeStatus);
        auctionBid.setAuction(auction);
        bidRepository.save(auctionBid);

        auction.setLastBidDate(new Date());
        auction.setLastBidValue(auctionBid.getBidValue());
        if (auctionBid.getUser() != null) {
            auction.setLastBidUser(auctionBid.getUser().getId());
        }
        auctionRepository.save(auction);
        return 1;
    }

    // ── Auction live check ────────────────────────────────────────────────────

    public int isAuctionLive(String auctionStartDateStr, Date lastBidDate,
                             String endTimeStr, int additionalMinutes) {
        try {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date startDate = sdf.parse(auctionStartDateStr);
            Date endDate = sdf.parse(endTimeStr);
            Date now = new Date();

            if (now.before(startDate)) return 0;
            if (now.after(endDate)) {
                if (lastBidDate != null) {
                    long diff = now.getTime() - lastBidDate.getTime();
                    long minutesDiff = diff / (1000 * 60);
                    if (minutesDiff <= additionalMinutes) return 1;
                }
                return 0;
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // ── Comment queries ───────────────────────────────────────────────────────

    public List<AuctionComment> getAuctionComments(Integer auctionId) {
        return commentRepository.findByAuctionId(auctionId);
    }

    public List<AuctionComment> getAuctionCommentsByUser(Integer auctionId, String email) {
        return commentRepository.findByAuctionIdAndUserEmail(auctionId, email);
    }

    @Transactional
    public int addComment(AuctionComment comment) {
        DictionaryItem answered = dictionaryItemRepository
                .findByKey("key.coment.answered").orElseThrow();
        comment.setRecordKey(UUID.randomUUID().toString());
        comment.setCommCreated(new Date());
        comment.setCreateDate(new Date());
        comment.setStatus(answered);
        commentRepository.save(comment);
        return 1;
    }

    // ── Revision queries ──────────────────────────────────────────────────────

    public List<AuctionRevisionFile> getRevisionFiles(Integer auctionId) {
        List<AuctionRevision> revisions = getRevisionFilesByRevision(auctionId);
        List<AuctionRevisionFile> files = new java.util.ArrayList<>();
        for (AuctionRevision rev : revisions) {
            if (rev.getFiles() != null) files.addAll(rev.getFiles());
        }
        return files;
    }

    public List<AuctionRevision> getRevisionFilesByRevision(Integer auctionId) {
        return revisionRepository.findByAuction_IdOrderByRevisionNumDesc(auctionId);
    }
}