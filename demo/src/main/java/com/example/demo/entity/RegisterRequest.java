package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "register_request")
public class RegisterRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Company info
    private String companyName;
    private String companyType;   // e.g. "შპს", "სს" etc — plain string for now
    private String category;      // plain string for now
    private String taxId;
    private String businessDesc;
    private String phisAddress;
    private String legalAddress;
    private Boolean vatPayer;
    private String bankCode1;
    private String bankAccount1;
    private String webSite;

    // Contact person
    private String contactName;
    private String contactSurname;
    private String contactPosition;
    private String contactEmail;
    private String contactPhone;
    private String contactMobile;

    // Meta
    private String status; // "PENDING", "APPROVED", "REJECTED"
    private LocalDateTime requestDate;

    // Files stored as byte arrays
    @Lob
    @Column(name = "reg_file", columnDefinition = "BLOB")
    private byte[] regFile;

    @Column(name = "reg_file_name")
    private String regFileName;

    @Lob
    @Column(name = "vat_file", columnDefinition = "BLOB")
    private byte[] vatFile;

    @Column(name = "vat_file_name")
    private String vatFileName;

    @Lob
    @Column(name = "other_file", columnDefinition = "BLOB")
    private byte[] otherFile;

    @Column(name = "other_file_name")
    private String otherFileName;
}