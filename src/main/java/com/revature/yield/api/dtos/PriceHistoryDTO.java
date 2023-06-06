package com.revature.yield.api.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class PriceHistoryDTO {

    /* todo
    *  https://api.coingecko.com/api/v3/simple/price?ids=near&vs_currencies=usd&include_market_cap=true&include_24hr_vol=true&include_24hr_change=true&include_last_updated_at=true&precision=full
    * */
    private String id;
    private double price;
    private double market_cap;
    private double total_volume_24hr;
    private double total_volume_24hr_change;
    private String date_time;

    private PriceHistoryDTO(Builder builder) {
        this.id = builder.id;;
        this.price = builder.price;
        this.market_cap = builder.market_cap;;
        this.total_volume_24hr = builder.total_volume_24hr;
        this.total_volume_24hr_change = builder.total_volume_24hr_change;
        this.date_time = builder.date_time;
    }

    public static class Builder {
        private String id;
        private double price;
        private double market_cap;
        private double total_volume_24hr;
        private double total_volume_24hr_change;
        private String date_time;

        public Builder(String id) {
            this.id = id;
        }

        public Builder withMarketCap(double market_cap) {
            this.market_cap = market_cap;
            return this;
        }

        public Builder withPrice(double price) {
            this.price = price;
            return this;
        }

        public Builder withTotalVolume24Hr(double totalVolume24Hr) {
            this.total_volume_24hr = totalVolume24Hr;
            return this;
        }

        public Builder withTotalVolume24HrChange(double totalVolume24HrChange) {
            this.total_volume_24hr_change = totalVolume24HrChange;
            return this;
        }

        public Builder withDateAndTime(String dateAndTime) {
            this.date_time = dateAndTime;
            return this;
        }

        public PriceHistoryDTO build() {
            return new PriceHistoryDTO(this);
        }

    }

}
