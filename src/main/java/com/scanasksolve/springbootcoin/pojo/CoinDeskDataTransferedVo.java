package com.scanasksolve.springbootcoin.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CoinDeskDataTransferedVo {
    String updateTime;
    String code;
    String symbol;
    Double rateFloat;
}
