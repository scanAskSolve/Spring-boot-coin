package com.scanasksolve.springbootcoin.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "currency_rate")
@Getter
@Setter
public class CurrencyRate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long currencyRateId;

    @Column
    Long currencyId;

    @Column
    Double rate;

    @Column
    String updateTime;

}
