package com.revature.yield.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

/* entity for creating a single profit and loss record comprised of one or many records
* */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "trade_reports")
public class TradeReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String date;
    private String reportDate;
    private String reportId;
    private String assetName;
    private String unitPrice;
    private String currencyPair;
    private String qty;
    private String amount;
    private String fee;
    private String profitLoss;
    private String costBasis;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private User user;
}
