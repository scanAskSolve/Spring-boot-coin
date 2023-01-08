package com.scanasksolve.springbootcoin.service;

import com.scanasksolve.springbootcoin.common.model.CoinDeskTemplateImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoinDeskService {

    @Autowired
    CoinDeskTemplateImpl coinDeskTemplate;
    public Object getCoinDeskData() {
        return coinDeskTemplate.getCoinDeskData();
    }
}
