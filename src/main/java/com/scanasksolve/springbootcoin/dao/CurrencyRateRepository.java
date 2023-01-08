package com.scanasksolve.springbootcoin.dao;

import com.scanasksolve.springbootcoin.entity.CurrencyRate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CurrencyRateRepository extends JpaRepository<CurrencyRate, Long> {
    CurrencyRate findCurrencyRateByCurrencyIdAndUpdateTime(Long currencyId,String updateTime);
}
