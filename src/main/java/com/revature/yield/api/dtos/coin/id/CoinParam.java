package com.revature.yield.api.dtos.coin.id;

import com.revature.yield.api.dtos.Param;
import lombok.*;

import static java.lang.String.format;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CoinParam extends Param {

    //    https://api.coingecko.com/api/v3/coins/bitcoin?localization=true&tickers=true&market_data=true&community_data=true&developer_data=true&sparkline=true

    /* pass the coin id (can be obtained from /coins) eg. bitcoin
     */
    private String id;

    /* Include all localized languages in response (true/false) [default: true]
     */
    private boolean localization = true;

    /* Include tickers data (true/false) [default: true]
     */
    private boolean tickers = true;

    /* Include market_data (true/false) [default: true]
     */
    private boolean market_data = true;

    /* Include community_data data (true/false) [default: true]
     */
    private boolean community_data = true;

    /* Include developer_data data (true/false) [default: true]
     */
    private boolean developer_data = true;

    /* Include sparkline 7 days data (eg. true, false) [default: false]
     */
    private boolean sparkline = false;


    public CoinParam(String id) {
        this.id = id;
    }

    public String toString() {
        return format("coins/%s?localization=%s&tickers=%s&market_data=%s&community_data=%s&developer_data=%s&sparkline=%s",
                id, localization, tickers, market_data, community_data, developer_data, sparkline);
    }

}
