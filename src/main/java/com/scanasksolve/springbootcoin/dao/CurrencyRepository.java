package com.scanasksolve.springbootcoin.dao;

import com.scanasksolve.springbootcoin.entity.Currency;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency getCurrenciesByCurrencyId(Long id);
    Currency getCurrencyByCurrencyName(String name);

}
