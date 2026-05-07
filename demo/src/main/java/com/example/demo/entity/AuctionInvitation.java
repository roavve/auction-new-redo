package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "AUCTION_INVITATION")
public class AuctionInvitation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "RECORDKEY")
    private String recordKey;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "COMP_USER_ID")
    private User companyUser;

    @ManyToOne
    @JoinColumn(name = "AUCTION_ID")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "STATUS_KEY", nullable = false)
    private DictionaryItem status;

    @Column(name = "DATE_SELECTED")
    private Date dateSelected;

    @Column(name = "DATE_INVITED")
    private Date dateInvited;

    @Column(name = "DATE_RECEIVED")
    private Date dateReceived;

    @Column(name = "DATE_ACCEPTED")
    private Date dateAccepted;

    @Column(name = "DATE_REJECTED")
    private Date dateRejected;

    @Column(name = "CANCEL_DATE")
    private Date cancelDate;

    @Column(name = "CLOSE_DATE")
    private Date closeDate;

    @Transient
    private String auctionName;

    @Transient
    private AuctionBid minBidValue;

    @Transient
    private AuctionBid maxBidValue;

    @Transient
    private AuctionBid myMinBidValue;

    @Transient
    private AuctionBid myMaxBidValue;

    @Transient
    private int isLastBidMine;

    @Transient
    private String timeLeft;
}