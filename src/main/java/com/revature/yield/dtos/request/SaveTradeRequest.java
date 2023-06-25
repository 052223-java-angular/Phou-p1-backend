package com.revature.yield.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class SaveTradeRequest extends Request {

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
