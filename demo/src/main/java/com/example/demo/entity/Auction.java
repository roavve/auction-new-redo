package com.example.demo.entity;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "AUCTION")
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "\"DESC\"")
    private String description;

    private String auctionType;
    private String status;

    private Double startBidValue;
    private Double currentHighestBid;
    private Double bidStep;
    private Double maxBidValue;

    private LocalDateTime bidStartDate;
    private LocalDateTime bidEndDate;

    private Integer additionalMinute;
    private Boolean showLastBid;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;
}