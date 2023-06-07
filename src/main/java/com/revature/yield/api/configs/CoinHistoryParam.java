package com.revature.yield.api.configs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import static java.lang.String.format;

@Getter
@Setter
public class CoinHistoryParam extends Param {

    // https://api.coingecko.com/api/v3/coins/near/history?date=12-12-2020&localization=false

    /* pass the coin id (can be obtained from /coins) eg. bitcoin
     */
    private String id;

    /* The date of data snapshot in dd-mm-yyyy eg. 30-12-2022
     */
    private LocalDateTime date;

    /* Set to false to exclude localized languages in response
    * */
    private boolean localization = false;

    public CoinHistoryParam(String id, LocalDateTime localDateTime) {
        this.id = id;
        this.date = localDateTime;
    }

    public String formatDateTime() {
        return format("%s-%s-%s", date.getDayOfMonth(), date.getMonthValue(), date.getYear());
    }

    public String toString() {
        return format("coins/%s/history?date=%s&localization=%s",
                id.toLowerCase(), formatDateTime(), localization);
    }

}
