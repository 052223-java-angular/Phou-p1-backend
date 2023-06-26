package com.revature.yield.dtos.request;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveTradeRequest extends Request {

    private String assetName;
    private String orderId;
    private String date;
    private String side;
    private String unitPrice;
    private String qty;
    private String amount;
    private String fee;
    private String currencyPair;
    private String userId;

}
