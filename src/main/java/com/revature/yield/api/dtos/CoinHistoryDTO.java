package com.revature.yield.api.dtos;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Getter
@Setter
public class CoinHistoryDTO {

    /* this class will retrieve the historical prices for an asset
    * */
    private String id;
    private String symbol;
    private String name;
    private String images;
    private double usd;
    private double btc;
    private double eth;
    private double bnb;
    private double total_volume;
    private double market_cap;
    private String date_time;

    private CoinHistoryDTO(Builder builder) {
        this.id = builder.id;
        this.symbol = builder.symbol;
        this.name = builder.name;
        this.images = builder.images;
        this.usd = builder.usd;
        this.btc = builder.btc;
        this.eth = builder.eth;
        this.bnb = builder.bnb;
        this.total_volume = builder.total_volume;
        this.market_cap = builder.market_cap;
        this.date_time = builder.date_time;
    }

    public static class Builder {
        private String id;
        private String symbol;
        private String name;
        private String images;
        private double usd;
        private double btc;
        private double eth;
        private double bnb;
        private double total_volume;
        private double market_cap;
        private String date_time;

        public Builder(String id) {
            this.id = id;
        }

        public Builder withSymbol(String symbol) {
            this.symbol = symbol;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withImages(String imageUrls) {
            this.images = imageUrls;
            return this;
        }

        public Builder withUsdPrice(double usdPrice) {
            this.usd = usdPrice;
            return this;
        }

        public Builder withBtcPrice(double btcPrice) {
            this.btc = btcPrice;
            return this;
        }

        public Builder withEthPrice(double ethPrice) {
            this.eth = ethPrice;
            return this;
        }

        public Builder withBnbPrice(double bnbPrice) {
            this.bnb = bnbPrice;
            return this;
        }

        public Builder withTotalVolume(double totalVolume) {
            this.total_volume = totalVolume;
            return this;
        }

        public Builder withMarketCap(double marketCap) {
            this.market_cap = marketCap;
            return this;
        }

        public Builder withDateTime(String dateTIme) {
            this.date_time = dateTIme;
            return this;
        }

        public CoinHistoryDTO build() {
            return new CoinHistoryDTO(this);
        }

    }

}
