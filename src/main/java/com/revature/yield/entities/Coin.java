package com.revature.yield.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "coins")
public class Coin implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String symbol;
    private String name;
    private String categories;
    private String imageUrls;
    private String genesisDate;
    private int watchListUsers;
    private int marketCapRank;
    private int geckoRank;
    private double geckoScore;
    private double devScore;
    private double communityScore;
    private double liquidityScore;

    @ManyToOne
    @JoinColumn(name = "market_data_id")
    @JsonBackReference
    private MarketData marketData;

    @ManyToOne
    @JoinColumn(name = "price_history_id")
    @JsonBackReference
    private PriceHistory priceHistories;

}
