package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "AUCTION")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "RECORDKEY")
    private String recordKey;

    @ManyToOne
    @JoinColumn(name = "AUCTION_TYPE", nullable = false)
    private DictionaryItem auctionType;

    @ManyToOne
    @JoinColumn(name = "ADMIN_ID", nullable = true)
    private User admin;

    @Column(name = "NAME")
    private String name;

    @Column(name = "\"DESC\"")
    private String desc;

    @ManyToOne
    @JoinColumn(name = "STATUS_KEY", nullable = false)
    private DictionaryItem status;

    @ManyToOne
    @JoinColumn(name = "AUCTION_STEP_KEY", nullable = true)
    private DictionaryItem auctionStep;

    @ManyToOne
    @JoinColumn(name = "AUCTION_PROJECT", nullable = true)
    private AuctionProject project;

    @ManyToOne
    @JoinColumn(name = "VALUE_TYPE_KEY", nullable = false)
    private DictionaryItem valueType;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "UOM_KEY", nullable = false)
    private DictionaryItem uom;

    @Column(name = "BID_STEP")
    private Double bidStep;

    @Column(name = "DISCUSS_START_DATE")
    private Date discussStartDate;

    @Column(name = "DISCUSS_END_DATE")
    private Date discussEndDate;

    @Column(name = "AUC_START_DATE")
    private Date auctionStartDate;

    @Column(name = "AUCT_END_DATE")
    private Date auctionEndDate;

    @Column(name = "INVITE_TEXT")
    private String inviteText;

    @Column(name = "START_BID_VALUE")
    private Double startBidValue;

    @Column(name = "MAX_BID_VALUE")
    private Double maxBidValue;

    @Column(name = "LAST_BID_VALUE")
    private Double lastBidValue;

    @Column(name = "LAST_BID_DATE")
    private Date lastBidDate;

    @Column(name = "LAST_BID_USER_ID")
    private Integer lastBidUser;

    @Column(name = "COUNT_INVIT")
    private Integer countInvitations;

    @Column(name = "COUNT_PART")
    private Integer countParticipants;

    @Column(name = "COUNT_ACTIVE")
    private Integer countActive;

    @Column(name = "COUNT_INACTIVE")
    private Integer countInactive;

    @Column(name = "ACTIVATE_DATE")
    private Date activateDate;

    @Column(name = "CANCEL_DATE")
    private Date cancelDate;

    @Column(name = "CLOSE_DATE")
    private Date closeDate;

    @Column(name = "SHOW_LAST_BID")
    private Boolean showLastBid;

    @Column(name = "START_DATE")
    private Date startDate;

    @Column(name = "END_DATE")
    private Date endDate;

    @Column(name = "START_TIME")
    private String startTime;

    @Column(name = "END_TIME")
    private String endTime;

    @Column(name = "ADDITIONAL_MINUTE")
    private Integer additionalMinute;

    @ManyToOne
    @JoinColumn(name = "CURRENCY_KEY", nullable = true)
    private DictionaryItem currency;

    @Column(name = "BID_START_DATE")
    private Date bidStartDate;

    @Column(name = "BID_END_DATE")
    private Date bidEndDate;

    @Column(name = "BID_START_TIME")
    private String bidStartTime;

    @Column(name = "BID_END_TIME")
    private String bidEndTime;

    @Column(name = "AUCTION_LAST_OFFER")
    private Double lastOffer;
}