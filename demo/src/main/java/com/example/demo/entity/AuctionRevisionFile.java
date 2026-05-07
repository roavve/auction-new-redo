package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "AUCTION_REVISION_FILE")
public class AuctionRevisionFile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "RECORDKEY")
    private String recordKey;

    @ManyToOne
    @JoinColumn(name = "AUCTION_ID")
    private Auction auction;

    @ManyToOne
    @JoinColumn(name = "REVISION_ID")
    private AuctionRevision revision;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_SIZE")
    private Long fileSize;

    @Column(name = "FILE_DESC")
    private String fileDescription;

    @Column(name = "FILE_FORMAT")
    private String fileFormat;

    @Lob
    @Column(name = "FILE_DATA", columnDefinition = "BLOB")
    private byte[] fileData;

    @Column(name = "FILE_DATE")
    private Date fileDate;
}