package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "AUCTION_PROJECT")
public class AuctionProject {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "PROJECT_SUM")
    private Double projectSum;

    @Column(name = "RECORDKEY")
    private String recordKey;
}