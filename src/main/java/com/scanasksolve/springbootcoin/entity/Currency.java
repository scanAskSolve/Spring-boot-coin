package com.scanasksolve.springbootcoin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "currency")
@Getter
@Setter
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long currencyId;

    @Column
    String currencyName;

    @Column
    String currencyCode;

    @Column
    String symbol;

    @Column
    String description;

    @Column
    String updateTime;

}
