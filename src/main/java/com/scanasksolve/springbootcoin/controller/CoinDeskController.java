package com.scanasksolve.springbootcoin.controller;

import com.scanasksolve.springbootcoin.service.CoinDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoinDeskController {
    @Autowired
    CoinDeskService coinDeskService;

    @PostMapping("/CoinDesk")
    Object insertCoinDesk() {
        return new ResponseEntity<String>((String) coinDeskService.getCoinDeskData(), HttpStatus.CREATED);
    }

}
