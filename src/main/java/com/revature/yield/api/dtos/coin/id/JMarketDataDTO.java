package com.revature.yield.api.dtos.coin.id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
public class JMarketDataDTO {

    public Map<String, String> current_price;
    public String total_value_locked;
    public String mcap_to_tvl_ratio;
    public String fdv_to_tvl_ratio;
    public String roi;
    public Map<String, String> ath;
    public Map<String, String> ath_change_percentage;
    public Map<String, String> ath_date;
    public Map<String, String> atl;
    public Map<String, String> atl_change_percentage;
    public Map<String, String> atl_date;
    public Map<String, String> market_cap;
    public String market_cap_rank;
    public Map<String, String> fully_diluted_valuation;
    public Map<String, String> total_volume;
    public Map<String, String> high_24h;
    public Map<String, String> low_24h;
    public String price_change_24h;
    public String price_change_percentage_24h;
    public String price_change_percentage_7d;
    public String price_change_percentage_14d;
    public String price_change_percentage_30d;
    public String price_change_percentage_60d;
    public String price_change_percentage_200d;
    public String price_change_percentage_1y;
    public String market_cap_change_24h;
    public String market_cap_change_percentage_24h;
    public Map<String, String> price_change_24h_in_currency;
    public Map<String, String> price_change_percentage_1h_in_currency;
    public Map<String, String> price_change_percentage_24h_in_currency;
    public Map<String, String> price_change_percentage_7d_in_currency;
    public Map<String, String> price_change_percentage_14d_in_currency;
    public Map<String, String> price_change_percentage_30d_in_currency;
    public Map<String, String> price_change_percentage_60d_in_currency;
    public Map<String, String> price_change_percentage_200d_in_currency;
    public Map<String, String> price_change_percentage_1y_in_currency;
    public Map<String, String> market_cap_change_24h_in_currency;
    public Map<String, String> market_cap_change_percentage_24h_in_currency;
    public String total_supply;
    public String max_supply;
    public String circulating_supply;
    public String last_updated;


}
