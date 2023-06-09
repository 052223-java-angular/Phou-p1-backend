package com.revature.yield.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "market_data")
public class MarketData implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private double currentPrice;
    private double totalValueLocked;
    private double mCapToTvlRatio;
    private double fdvToTvlRatio;
    private double roi;
    private double ath;
    private double athChangePercent;
    private String athDate;
    private double atl;
    private double atlChangePercent;
    private String atlDate;
    private double marketCap;
    private int marketCapRank;
    private double fullyDilutedValuation;
    private double totalVolume;
    private double high24Hr;
    private double low24hr;
    private double priceChange24Hr;
    private double priceChangePercent24Hr;
    private long totalSupply;
    private long maxSupply;
    private double circulatingSupply;
    private String marketSnapShotDate;

    @OneToMany(mappedBy = "marketData", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Coin> coins;

}
