package com.revature.yield.api.dtos.coin.history;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class JHistoryMarketDataDTO {

    public Map<String, String> current_price;
    public Map<String, String> market_cap;
    public Map<String, String>  total_volume;

}
