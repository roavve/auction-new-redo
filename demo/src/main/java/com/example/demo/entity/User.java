package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.UUID;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "USER_USER")
public class User {

    public static final int STATUS_INACTIVE  = 0;
    public static final int STATUS_ACTIVE    = 1;
    public static final int STATUS_CANCELLED = 2;
    public static final int STATUS_BLOCKED   = 3;

    @Transient
    private String salt = UUID.randomUUID().toString().substring(32);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "COMPANY_ID")
    private Company company;

    @Column(name = "RECORD_KEY")
    private String recordKey;

    @Column(name = "ROLE")
    private String role;

    @Column(name = "IS_INTERNAL")
    private Boolean internal;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "USER_NAME")
    private String email;

    @Column(name = "USER_PASS")
    private String password;

    @Column(name = "CONTACT_POSITION")
    private String contactPosition;

    @Column(name = "CONTACT_MAIL")
    private String contactEmail;

    @Column(name = "CONTACT_PHONE")
    private String contactPhone;

    @Column(name = "CONTACT_MOBILE")
    private String contactMobile;

    @Column(name = "REGISTER_DATE")
    private Date registerDate;

    @Column(name = "CONFIRM_EMAIL_DATE")
    private Date confirmEmailDate;

    @Column(name = "ACTIVATE_DATE")
    private Date activateDate;

    @Column(name = "IS_ACTIVE")
    private Boolean active;

    @Column(name = "LOCK_DATE")
    private Date lockDate;

    @Column(name = "IS_LOCKED")
    private Boolean locked;

    @Column(name = "LOGIN_DATE")
    private Date loginDate;

    @Column(name = "IS_CANCELLED")
    private Boolean cancelled;

    @Column(name = "CANCELL_DATE")
    private Date cancelledDate;

    @Column(name = "USER_STATUS")
    private int status;

    @Column(name = "ACTIVATE_CODE")
    private String actKey = UUID.randomUUID().toString();

    @Column(name = "IS_EXTERNAL")
    private Boolean external;
}