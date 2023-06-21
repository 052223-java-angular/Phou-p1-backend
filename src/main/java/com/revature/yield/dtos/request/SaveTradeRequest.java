package com.revature.yield.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class SaveTradeRequest {

    private String asset;
    private String orderId;
    private String date;
    private String side;
    private String unitPrice;
    private String qty;
    private String amountPaid;
    private String fee;
    private String currencyPair;
    private String userId;

}
