package com.revature.yield.api.configs;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

import static java.lang.String.format;

@Builder
@Getter
@Setter
public class CoinHistoryParam extends Param {

    // https://api.coingecko.com/api/v3/coins/near/history?date=12-12-2020&localization=false

    /* pass the coin id (can be obtained from /coins) eg. bitcoin
     */
    private String id;

    /* The date of data snapshot in dd-mm-yyyy eg. 30-12-2022
     */
    private LocalDate date;

    /* Set to false to exclude localized languages in response
    * */
    private boolean localization = false;

    public String toString() {
        return format("%s/history?date=%s&localization=%s",
                id, format("%s-%s-%s", date.getDayOfMonth(), date.getMonth(), date.getYear()), localization);
    }

}
