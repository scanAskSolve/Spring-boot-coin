package com.scanasksolve.springbootcoin.service;

import com.google.gson.Gson;
import com.scanasksolve.springbootcoin.common.model.CoinDeskTemplateImpl;
import com.scanasksolve.springbootcoin.dao.CurrencyRateRepository;
import com.scanasksolve.springbootcoin.dao.CurrencyRepository;
import com.scanasksolve.springbootcoin.entity.Currency;
import com.scanasksolve.springbootcoin.entity.CurrencyRate;
import com.scanasksolve.springbootcoin.pojo.CoinDeskDataTransferedVo;
import com.scanasksolve.springbootcoin.pojo.WebCoinDeskBpiPo;
import com.scanasksolve.springbootcoin.pojo.WebCoinDeskPo;
import com.scanasksolve.springbootcoin.pojo.WebCoinDeskTimePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
public class CoinDeskService {


    @Autowired
    CoinDeskTemplateImpl coinDeskTemplate;

    @Autowired
    CurrencyRepository currencyRepository;

    @Autowired
    CurrencyRateRepository currencyRateRepository;

    public WebCoinDeskPo getCoinDeskData() {
        Gson gson = new Gson();
        WebCoinDeskPo webCoinDeskPo = null;
        try {
            webCoinDeskPo = gson.fromJson(coinDeskTemplate.getCoinDeskData(), WebCoinDeskPo.class);
        } catch (Exception e) {
            log.error(e.toString());
        }
        return webCoinDeskPo;
    }

    public List<CoinDeskDataTransferedVo> getCoinDeskDataTransfered(WebCoinDeskPo webCoinDeskPo) {
        List<CoinDeskDataTransferedVo> coinDeskDataTransferedVoList = new ArrayList<>();
        try {
            saveCurrency(webCoinDeskPo.getBpi());
            String lastUpdateTime = getLastUpdateTime(webCoinDeskPo.getTime());
            saveCurrentRate(webCoinDeskPo.getBpi(), lastUpdateTime);

            webCoinDeskPo.getBpi().forEach((index, value) -> {
                CoinDeskDataTransferedVo coinDeskDataTransferedVo = CoinDeskDataTransferedVo.builder()
                        .code(value.getCode())
                        .symbol(value.getSymbol())
                        .rateFloat(value.getRateFloat())
                        .updateTime(lastUpdateTime).build();
                coinDeskDataTransferedVoList.add(coinDeskDataTransferedVo);
            });

        } catch (Exception e) {
            log.error(e.toString());
        }
        return coinDeskDataTransferedVoList;
    }

    public String getLastUpdateTime(WebCoinDeskTimePo webCoinDeskTimePo) throws ParseException {
        Date date = new Date();
        if (webCoinDeskTimePo == null)
            return null;
        else if (webCoinDeskTimePo.getUpdated() != null) {
            DateFormat DF_UTC = new SimpleDateFormat("MMMM d, yyyy hh:mm:ss 'UTC'", Locale.ENGLISH);
            date = DF_UTC.parse(webCoinDeskTimePo.getUpdated());

        } else if (webCoinDeskTimePo.getUpdatedISO() != null) {

            LocalDateTime localDate = LocalDateTime.parse(webCoinDeskTimePo.getUpdatedISO());
            Instant instant = localDate.atZone(ZoneId.systemDefault()).toInstant();
            date = Date.from(instant);
        } else {
            DateFormat updatedukFormatter = new SimpleDateFormat("MMMM d, yyyy 'at' hh:mm zzz", Locale.ENGLISH);
            updatedukFormatter.setTimeZone(TimeZone.getTimeZone(ZoneId.of("Europe/London")));
            date = updatedukFormatter.parse(webCoinDeskTimePo.getUpdateduk());
        }
        return new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
    }

    private void saveCurrency(Map<String, WebCoinDeskBpiPo> bpi) {
        for (String webCoinDeskBpiPoKey : bpi.keySet()) {
            WebCoinDeskBpiPo bpiPo = bpi.get(webCoinDeskBpiPoKey);
            Currency currency = currencyRepository.getCurrencyByCurrencyName(webCoinDeskBpiPoKey);
            if (currency == null) {
                currency = Currency.builder()
                        .currencyName(webCoinDeskBpiPoKey)
                        .currencyCode(bpiPo.getCode())
                        .symbol(bpiPo.getSymbol())
                        .description(bpiPo.getDescription()).build();
                currencyRepository.save(currency);
            }
        }
    }

    private List<CurrencyRate> saveCurrentRate(Map<String, WebCoinDeskBpiPo> bpi, String updated) {
        List<CurrencyRate> currencyRateList = new ArrayList<>();
        for (String webCoinDeskBpiPoKey : bpi.keySet()) {
            WebCoinDeskBpiPo bpiPo = bpi.get(webCoinDeskBpiPoKey);
            Currency currency = currencyRepository.getCurrencyByCurrencyName(webCoinDeskBpiPoKey);
            if (currency.getCurrencyId() != null) {
                CurrencyRate currencyRate = currencyRateRepository.findCurrencyRateByCurrencyIdAndUpdateTime(currency.getCurrencyId(), updated);
                if (currencyRate == null)
                    currencyRate = CurrencyRate.builder()
                            .currencyId(currency.getCurrencyId())
                            .rate(bpiPo.getRateFloat())
                            .updateTime(updated)
                            .build();
                currencyRateList.add(currencyRate);
                currencyRateRepository.save(currencyRate);
            }
        }
        return currencyRateList;
    }

    public Currency getCurrencyByCurrencyName(String name) {
        return currencyRepository.getCurrencyByCurrencyName(name);
    }

    public Currency insertCurrency(Currency currency) {
        return currencyRepository.save(currency);
    }

    public Currency putCurrency(Currency currency, Long id) {
        Currency targetCurrency = currencyRepository.getCurrenciesByCurrencyId(id);
        if (targetCurrency != null) {
            BeanUtils.copyProperties(currency, targetCurrency);
            targetCurrency.setCurrencyId(id);
            return currencyRepository.save(targetCurrency);
        }
        return null;
    }

    public Currency getCurrencyByCurrencyId(Long id) {
        return currencyRepository.getCurrenciesByCurrencyId(id);
    }

    public List<Currency> getCurrency() {
        return currencyRepository.findAll();
    }

    public void deleteCurrencyByCurrencyId(Long id) {
        Currency targetCurrency = currencyRepository.getCurrenciesByCurrencyId(id);
        if (targetCurrency != null) {
            currencyRepository.delete(targetCurrency);
        }
    }
}
