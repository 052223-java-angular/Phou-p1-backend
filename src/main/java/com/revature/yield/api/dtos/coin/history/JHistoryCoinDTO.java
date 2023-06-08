package com.revature.yield.api.dtos.coin.history;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@ToString
@JsonIgnoreProperties({"localization", "public_interest_stats"})
public class JHistoryCoinDTO {

    public String id;
    public String symbol;
    public String name;
    public Map<String, String> image;
    public JHistoryMarketDataDTO market_data;
    public JHistoryCommunityDataDTO community_data;
    public JHistoryDeveloperData developer_data;



}
