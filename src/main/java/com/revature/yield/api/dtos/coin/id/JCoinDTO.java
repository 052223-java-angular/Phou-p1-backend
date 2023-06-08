package com.revature.yield.api.dtos.coin.id;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@ToString
@Getter
@Setter
@JsonIncludeProperties({"id", "symbol", "name", "categories", "image", "genesis_date", "watchlist_portfolio_users",
"market_cap_rank", "coingecko_rank", "coingecko_score", "developer_score", "community_score", "liquidity_score",
"market_data", "developer_data", "last_updated"})
public class JCoinDTO {

    public String id;
    public String symbol;
    public String name;
    public List<String> categories;
    public Map<String, String> image;
    public String genesis_date;
    public String watchlist_portfolio_users;
    public String market_cap_rank;
    public String coingecko_rank;
    public String coingecko_score;
    public String developer_score;
    public String community_score;
    public String liquidity_score;
    public JMarketDataDTO market_data;
    public JDeveloperDataDTO developer_data;
    public String last_updated;


}
