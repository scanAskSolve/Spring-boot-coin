package com.scanasksolve.springbootcoin.controller;

import com.scanasksolve.springbootcoin.entity.Currency;
import com.scanasksolve.springbootcoin.entity.CurrencyRate;
import com.scanasksolve.springbootcoin.pojo.CoinDeskDataTransferedVo;
import com.scanasksolve.springbootcoin.pojo.WebCoinDeskPo;
import com.scanasksolve.springbootcoin.service.CoinDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CoinDeskController {
    @Autowired
    CoinDeskService coinDeskService;

    @PostMapping("/CoinDeskWeb")
    public ResponseEntity<WebCoinDeskPo> insertCoinDeskByWeb() {
        return new ResponseEntity<WebCoinDeskPo>(coinDeskService.getCoinDeskData(), HttpStatus.OK);
    }
    @PostMapping("/CoinDeskTransfered")
    public ResponseEntity<List<CoinDeskDataTransferedVo>> TransferedCoinDeskByWeb() {

        return new ResponseEntity<List<CoinDeskDataTransferedVo>>(coinDeskService.getCoinDeskDataTransfered(insertCoinDeskByWeb().getBody()), HttpStatus.OK);
    }

    @PostMapping("/CoinDesk")
    public Object insertCoinDesk(@RequestBody Currency currency) {
        if (coinDeskService.getCurrencyByCurrencyName(currency.getCurrencyName()) == null) {
            return new ResponseEntity<Currency>(coinDeskService.insertCurrency(currency), HttpStatus.OK);
        }
        return new ResponseEntity<String>("duplicates CurrencyName in the database ", HttpStatus.OK);
    }

    @PutMapping("/CoinDesk/{id}")
    public ResponseEntity<Currency> putCoinDesk(@RequestBody Currency currency, @PathVariable Long id) {
        return new ResponseEntity<Currency>(coinDeskService.putCurrency(currency, id), HttpStatus.OK);
    }

    @GetMapping("/CoinDesk/{id}")
    public Object getCoinDesk(@PathVariable Long id) {
        return new ResponseEntity<Currency>(coinDeskService.getCurrencyByCurrencyId(id), HttpStatus.OK);
    }

    @GetMapping("/CoinDesk")
    public Object putCoinDesk() {
        return new ResponseEntity<List<Currency>>(coinDeskService.getCurrency(), HttpStatus.OK);
    }

    @DeleteMapping("/CoinDesk/{id}")
    public ResponseEntity<String> deleteCoinDesk(@PathVariable Long id) {
        coinDeskService.deleteCurrencyByCurrencyId(id);
        return new ResponseEntity<String>("", HttpStatus.OK);
    }
}
