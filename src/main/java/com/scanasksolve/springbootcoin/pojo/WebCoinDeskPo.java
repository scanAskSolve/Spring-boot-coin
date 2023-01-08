package com.scanasksolve.springbootcoin.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@Data
public class WebCoinDeskPo {

    @JsonProperty("time")
    private WebCoinDeskTimePo time;
    @JsonProperty("disclaimer")
    private String disclaimer;
    @JsonProperty("chartName")
    private String chartName;
    @JsonProperty("bpi")
    private Map<String, WebCoinDeskBpiPo> bpi;

}
