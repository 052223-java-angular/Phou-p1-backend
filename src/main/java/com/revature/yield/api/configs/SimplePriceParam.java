package com.revature.yield.api.configs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static java.lang.String.format;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class SimplePriceParam extends Param {

    //    https://api.coingecko.com/api/v3/simple/price?ids=Bitcoin&vs_currencies=usd&include_market_cap=true&include_24hr_vol=true&include_24hr_change=true&include_last_updated_at=true&precision=full

    /* id of coins, comma-separated if querying more than 1 coin
     */
    private String[] ids;

    /* vs_currency of coins, comma-separated if querying more than 1 vs_currency
     */
    private String[] vs_currencies;

    /* true/false to include market_cap, default: false
     */
    private boolean include_market_cap = true;

    /* true/false to include 24hr_vol, default: false
     */
    private boolean include_24hr_vol = true;

    /* true/false to include 24hr_change, default: false
     */
    private boolean include_24hr_change = true;

    /* true/false to include last_updated_at of price, default: false
     */
    private boolean include_last_updated_at = true;

    /* full or any value between 0 - 18 to specify decimal place for currency price value
     */
    private String precision = "full";

    public SimplePriceParam(String[] coinsIds, String[] vs_currencies) {
        this.ids = coinsIds;
        this.vs_currencies = vs_currencies;
    }

    public String toString() {
        return format("simple/price?ids=%s&vs_currencies=%s&include_market_cap=%s&include_24hr_vol=%s&include_24hr_change=%s&include_last_updated_at=%s&precision=%s",
                String.join(",", ids).toLowerCase(), String.join(",", vs_currencies).toLowerCase(), include_market_cap, include_24hr_vol, include_24hr_change, include_last_updated_at, precision);
    }

}
