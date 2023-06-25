package com.revature.yield.dtos.request;
import com.revature.yield.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class UpdateTradeRequest extends Request {


    private UUID id;
    private String reportDate;
    private String reportId;
    private String buyValue;
    private String sellValue;
    private String buyFee;
    private String sellFee;
    private String boughtQty;
    private String soldQty;
    private String buyDate;
    private String sellDate;
    private String pAndI;

    // new fields - update erd
    private String orderId;
    private String assetName;
    private String unitPrice;
    private String currencyPair;

    private User user;

}
