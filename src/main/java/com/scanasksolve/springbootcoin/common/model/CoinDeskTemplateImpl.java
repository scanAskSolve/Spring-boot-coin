package com.scanasksolve.springbootcoin.common.model;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Component
public class CoinDeskTemplateImpl implements CoinDeskTemplate {

    final private String COIN_URL = "https://api.coindesk.com/v1/bpi/currentprice.json";

    @Override
    public String getCoinDeskData() {
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity(new URI(COIN_URL), String.class);
            return response.getBody();
        }catch (Exception e){
            log.error(e.toString());
        }
        return null;
    }
}
