package com.revature.yield.dtos.request;
import com.revature.yield.entities.User;
import lombok.*;

import java.util.UUID;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTradeRequest extends Request {

    private UUID id;
    private String reportDate;
    private String reportId;
    private String assetName;
    private String currencyPair;
    private String buyValue;
    private String sellValue;
    private String buyFee;
    private String sellFee;
    private String boughtQty;
    private String soldQty;
    private String buyDate;
    private String sellDate;
    private String orderId;
    private String unitPrice;
    private String side;
    private String date;
    private User user;

}
