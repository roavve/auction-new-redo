package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "AUCTION_REVISION")
public class AuctionRevision {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "RECORDKEY")
    private String recordKey;

    @ManyToOne
    @JoinColumn(name = "AUCTION_ID")
    private Auction auction;

    @Column(name = "REVISION_NUM")
    private Integer revisionNum;

    @Column(name = "REVISION_DATE")
    private Date revisionDate;

    @Column(name = "IS_CURRENT")
    private Boolean current;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @OneToMany(mappedBy = "revision", fetch = FetchType.LAZY)
    private List<AuctionRevisionFile> files;
}