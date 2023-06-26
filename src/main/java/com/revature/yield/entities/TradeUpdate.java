package com.revature.yield.entities;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TradeUpdate extends Trade {

    private String buyAmount;
    private String sellAmount;
    private String buyFee;
    private String sellFee;
    private String boughtQty;
    private String soldQty;
    private String buyDate;
    private String sellDate;

}
