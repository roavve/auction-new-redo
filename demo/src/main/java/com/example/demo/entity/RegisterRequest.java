package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "register_request")
public class RegisterRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String companyType;
    private String category;
    private String taxId;
    private String businessDesc;
    private String phisAddress;
    private String legalAddress;
    private Boolean vatPayer;
    private String bankCode1;
    private String bankAccount1;
    private String webSite;

    private String contactName;
    private String contactSurname;
    private String contactPosition;
    private String contactEmail;
    private String contactPhone;
    private String contactMobile;

    private String status;

    @Column(name = "request_date")
    private Date requestDate;

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