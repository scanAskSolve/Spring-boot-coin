package com.scanasksolve.springbootcoin.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"updateTime", "currencyId"})})
@Getter
@Setter
@Builder
@NoArgsConstructor
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

    public CurrencyRate(Long currencyRateId, Long currencyId, Double rate, String updateTime) {
        this.currencyRateId = currencyRateId;
        this.currencyId = currencyId;
        this.rate = rate;
        this.updateTime = updateTime;
    }
}
