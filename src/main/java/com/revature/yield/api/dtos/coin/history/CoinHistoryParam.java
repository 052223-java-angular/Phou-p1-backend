package com.revature.yield.api.dtos.coin.history;

import com.revature.yield.api.dtos.Param;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate localDate;

    /* Set to false to exclude localized languages in response
    * */
    private boolean localization = false;

    public CoinHistoryParam(String id, LocalDate localDate) {
        this.id = id;
        this.localDate = localDate;
    }

    public CoinHistoryParam(String id) {
        this.id = id;
    }

    public String formatDate() {
        return format("%s-%s-%s", localDate.getDayOfMonth(),  localDate.getMonthValue(), localDate.getYear());
    }

    public String toString() {
        return format("coins/%s/history?date=%s&localization=%s",
                id.toLowerCase(), formatDate(), localization);
    }

}
