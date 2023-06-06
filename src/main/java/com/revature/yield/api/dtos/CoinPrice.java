package com.revature.yield.api.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CoinPrice {


    /* todo
    *   this will retrieve the current price of a asset
    *  https://api.coingecko.com/api/v3/simple/price?ids=near&vs_currencies=usd&include_market_cap=true&include_24hr_vol=true&include_24hr_change=true&include_last_updated_at=true&precision=full
    * */
    private String id;
    private String currency_price;

}
