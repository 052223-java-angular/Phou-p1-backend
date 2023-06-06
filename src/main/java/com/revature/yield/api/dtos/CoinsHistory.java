package com.revature.yield.api.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CoinsHistory {

    /* this class will retrieve the historical prices for an asset
    * */
    private String id;
    private String symbol;
    private String name;
    private String images;
    private String market_data_current_price;
    private String market_data_market_cap;
    private String market_data_total_volume;

}
