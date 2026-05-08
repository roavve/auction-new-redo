package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "AUCTION_COMMENT")
public class AuctionComment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "RECORDKEY")
    private String recordKey;

    @ManyToOne
    @JoinColumn(name = "AUCTION_ID")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "COMM_TEXT", length = 2000)
    private String commText;

    @Column(name = "COMM_CREATED")
    private Date commCreated;

    @Column(name = "IS_ADMIN")
    private Boolean admin;

    @ManyToOne
    @JoinColumn(name = "STATUS_KEY")
    private DictionaryItem status;

    @Column(name = "ANSWER_TOKEY")
    private String answerToKey;

    @Column(name = "CREATE_DATE")
    private Date createDate;
}