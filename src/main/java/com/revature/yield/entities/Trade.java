package com.revature.yield.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trades")
public class Trade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String reportDate;
    private String reportId;
    private String buyValue;
    private String sellValue;
    private String buyFee;
    private String sellFee;
    private String boughtQty;
    private String soldQty;
    private String buyDate;
    private String sellDate;
    private String pAndI;

    // new fields - update erd
    private String orderId;
    private String assetName;
    private String unitPrice;
    private String currencyPair;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "coin_id")
//    @JsonBackReference
//    private Coin coin;

}
