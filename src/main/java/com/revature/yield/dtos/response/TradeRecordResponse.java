package com.revature.yield.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


/* This is the entity for storing the raw model
* */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class TradeRecordResponse {

    private UUID id;
//    private String reportDate;
//    private String reportId;
    private String buyValue;
    private String sellValue;
    private String buyFee;
    private String sellFee;
    private String boughtQty;
    private String soldQty;
    private String buyDate;
    private String sellDate;
    private String addDate;

    // new fields - update erd
    private String orderId;
    private String assetName;
    private String unitPrice;
    private String currencyPair;

}
