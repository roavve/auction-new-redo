package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "DICTIONARY")
public class Dictionary {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "\"KEY\"")
    private String key;

    @Column(name = "NAME")
    private String name;
}