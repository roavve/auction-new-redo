package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "COMPANY")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "RECORD_KEY")
    private String recordKey;

    @Column(name = "NAME")
    private String companyName;

    @ManyToOne
    @JoinColumn(name = "TYPE_KEY", nullable = true)
    private DictionaryItem type;

    @ManyToOne
    @JoinColumn(name = "STATUS_KEY", nullable = true)
    private DictionaryItem status;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = true)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "SUB_CATEGORY_ID", nullable = true)
    private Category subCategory;

    @Column(name = "TAXID")
    private String taxId;

    @Column(name = "BUSINESS_DESC")
    private String businessDesc;

    @Column(name = "PHIS_ADDRESS")
    private String phisAddress;

    @Column(name = "LEGAL_ADDRESS")
    private String legalAddress;

    @Column(name = "IS_VAT_PAYER")
    private Boolean vatPayer;

    @Column(name = "BANK_CODE1")
    private String bankCode1;

    @Column(name = "BANK_ACCOUNT1")
    private String bankAccount1;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "FLOW_DATE_CREATED")
    private Date flowDateCreated;

    @Column(name = "FLOW_DATE_INVITED")
    private Date flowDateInvited;

    @Column(name = "FLOW_DATE_REGISTERED")
    private Date flowDateRegistered;

    @Column(name = "FLOW_DATE_ACTIVATED")
    private Date flowDateActivated;

    @Column(name = "FLOW_DATE_CANCELLED")
    private Date flowDateCancelled;

    @Column(name = "FLOW_CREATED_BY")
    private String flowCreatedBy;

    @Column(name = "FLOW_INVITED_BY")
    private String flowInvitedBy;

    @Column(name = "FLOW_CANCELLED_BY")
    private String flowCancelledBy;

    @Column(name = "ADMIN_USER")
    private String adminUser;

    @Column(name = "FROM_REQ_ID")
    private Integer fromReqId;

    @Column(name = "CONTACT_EMAIL")
    private String contactEmail;

    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    @Column(name = "CONTACT_NAME")
    private String contactName;

    @Column(name = "CONTACT_LASTNAME")
    private String contactSurname;

    @Column(name = "CONTACT_POSITION")
    private String contactPosition;

    @Column(name = "CONTACT_MOBILE")
    private String contactMobile;

    @Column(name = "WEB_SITE")
    private String webSite;

    @Transient
    private String compCategory;
}