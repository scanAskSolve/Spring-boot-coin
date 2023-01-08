package com.scanasksolve.springbootcoin;

import com.scanasksolve.springbootcoin.controller.CoinDeskController;
import com.scanasksolve.springbootcoin.entity.Currency;
import com.scanasksolve.springbootcoin.pojo.CoinDeskDataTransferedVo;
import com.scanasksolve.springbootcoin.pojo.WebCoinDeskBpiPo;
import com.scanasksolve.springbootcoin.pojo.WebCoinDeskPo;
import com.scanasksolve.springbootcoin.pojo.WebCoinDeskTimePo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.matchesPattern;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestController {

    @Autowired
    CoinDeskController controller;

    @Before
    public void before() {
        System.out.println("單元測試開始");
    }

    @Test
    public void testCoinDeskByWeb() {

        ResponseEntity<WebCoinDeskPo> responseEntity = controller.insertCoinDeskByWeb();

        WebCoinDeskPo actualData = responseEntity.getBody();
        WebCoinDeskPo expectedData = new WebCoinDeskPo();
        expectedData.setTime(new WebCoinDeskTimePo("Jan 8, 2023 18:17:00 UTC", "2023-01-08T18:17:00+00:00", "Jan 8, 2023 at 18:17 GMT"));
        expectedData.setDisclaimer("This data was produced from the CoinDesk Bitcoin Price Index (USD). Non-USD currency data converted using hourly conversion rate from openexchangerates.org");
        expectedData.setChartName("Bitcoin");
        Map<String, WebCoinDeskBpiPo> bpi = new HashMap<>();
        bpi.put("USD", new WebCoinDeskBpiPo("USD", "&#36;", "16,935.4736", "United States Dollar", 16935.4736));
        bpi.put("GBP", new WebCoinDeskBpiPo("GBP", "&pound;", "14,151.1463", "British Pound Sterling", 14151.1463));
        bpi.put("EUR", new WebCoinDeskBpiPo("EUR", "&euro;", "16,497.6239", "Euro", 16497.6239));
        expectedData.setBpi(bpi);

        Assert.assertNotNull(actualData);
        Map<String, WebCoinDeskBpiPo> actualDataBpi = actualData.getBpi();
        assertThat(actualDataBpi).hasSize(3);
        assertThat(actualDataBpi).containsKeys("USD", "GBP", "EUR");
        assertThat(actualData)
                .extracting(WebCoinDeskPo::getTime)
                .extracting("updated", "updatedISO")
                .contains("Jan 8, 2023 19:26:00 UTC", "2023-01-08T19:26:00+00:00");
        Assert.assertEquals(200, responseEntity.getStatusCodeValue());
    }
    @Test
    public void testCoinDeskTransfered() {

        ResponseEntity<List<CoinDeskDataTransferedVo>> responseEntity = controller.TransferedCoinDeskByWeb();
        List<CoinDeskDataTransferedVo> coinDeskDataTransferedVoList = new ArrayList<>();
        coinDeskDataTransferedVoList.add(CoinDeskDataTransferedVo.builder()
                .code("USD")
                .symbol("&#36;")
                .rateFloat(16940.3092)
                .updateTime("2023/01/08 20:19:00").build());
        coinDeskDataTransferedVoList.add(CoinDeskDataTransferedVo.builder()
                .code("GBP")
                .symbol("&pound;")
                .rateFloat(14155.1868)
                .updateTime("2023/01/08 20:19:00").build());
        coinDeskDataTransferedVoList.add(CoinDeskDataTransferedVo.builder()
                .code("EUR")
                .symbol("&euro;")
                .rateFloat(16502.3344)
                .updateTime("2023/01/08 20:19:00").build());
        assertThat(responseEntity.getBody()).hasSameSizeAs(coinDeskDataTransferedVoList);
        assertThat(responseEntity.getBody()).containsExactlyInAnyOrderElementsOf(coinDeskDataTransferedVoList);
    }
    @Test
    public void testInsertCoinDesk() {
        Currency currency=Currency.builder().currencyName("NTD").currencyCode("NTD").symbol("$").description("new TW dollar").build();
        ResponseEntity<Currency> responseEntity = (ResponseEntity<Currency>)controller.insertCoinDesk(currency);
        Currency respond = new Currency();
        BeanUtils.copyProperties(currency,respond);
        respond.setCurrencyId(1L);

        Assert.assertEquals(respond.toString(), Objects.requireNonNull(responseEntity.getBody()).toString());

    }
    @Test
    public void testPutCoinDesk() {
        Currency currency=Currency.builder().currencyName("NTD").currencyCode("NTD").symbol("$").description("new TW dollar").build();
        controller.insertCoinDesk(currency);
        Currency putCurrency=Currency.builder().currencyId(1L).currencyName("NTD").currencyCode("NTD").symbol("$").description("new TW dollars").build();
        ResponseEntity<Currency> responseEntity = controller.putCoinDesk(putCurrency, 1L);

        Currency respond = new Currency();
        BeanUtils.copyProperties(putCurrency,respond);

        Assert.assertEquals(respond.toString(), Objects.requireNonNull(responseEntity.getBody()).toString());

    }
    @Test
    public void testGetCoinDesk() {
        Currency currency=Currency.builder().currencyName("NTD").currencyCode("NTD").symbol("$").description("new TW dollar").build();
        controller.insertCoinDesk(currency);
        ResponseEntity<Currency> responseEntity = (ResponseEntity<Currency>)controller.getCoinDesk( 1L);
        Currency respond = new Currency();
        BeanUtils.copyProperties(currency,respond);
        respond.setCurrencyId(1L);
        Assert.assertEquals(respond.toString(), Objects.requireNonNull(responseEntity.getBody()).toString());

    }
    @Test
    public void testDelCoinDesk() {
        Currency currency=Currency.builder().currencyName("NTD").currencyCode("NTD").symbol("$").description("new TW dollar").build();
        controller.insertCoinDesk(currency);
        ResponseEntity<String> responseEntity = controller.deleteCoinDesk( 1L);
        Assert.assertEquals("", responseEntity.getBody());
        ResponseEntity<Currency> CurrencyEntity = (ResponseEntity<Currency>)controller.getCoinDesk( 1L);
        Assert.assertNull(CurrencyEntity.getBody());
    }
    @After
    public void after() {
        System.out.println("單元測試結束");
    }
}
