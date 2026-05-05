package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role; // "USER", "ADMIN"

    @Column(name = "status")
    private int status; // 0=inactive, 1=active, 2=cancelled, 3=blocked

    @Column(name = "active")
    private Boolean active;

    @Column(name = "locked")
    private Boolean locked;

    @Column(name = "act_key")
    private String actKey;

    @Column(name = "contact_position")
    private String contactPosition;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "contact_mobile")
    private String contactMobile;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @Column(name = "login_date")
    private LocalDateTime loginDate;
}