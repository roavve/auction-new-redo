package com.example.demo.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "DI_ITEM")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class DictionaryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "FIXED")
    private boolean fixed;

    @Column(name = "\"KEY\"")
    private String key;

    @Column(name = "NAME", nullable = true)
    private String name;

    @Column(name = "NAME_GE")
    private String nameGE;

    @Column(name = "SORT_ORDER")
    private int sortOrder;

    @Column(name = "DISABLED")
    private boolean disabled;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DICT_ID", nullable = false)
    private Dictionary dictionary;
}