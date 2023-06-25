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
@Table(name = "price_history")
public class PriceHistory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private double price;
    private double marketCap;
    private double totalVolume;
    private String snapshot_date;

    @OneToMany(mappedBy = "priceHistories", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Coin> coins;

}
