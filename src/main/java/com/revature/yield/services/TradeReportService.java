package com.revature.yield.services;

import com.revature.yield.entities.Trade;
import com.revature.yield.entities.TradeReport;
import com.revature.yield.repositories.ITradeRecordRepo;
import com.revature.yield.repositories.ITradeReportRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class TradeReportService {

    private final ITradeReportRepo tradeReportRepo;

    private final ITradeRecordRepo tradeRecordRepo;

    public Set<TradeReport> getTradeReports() {

        // todo impl
        return null;
    }


    public void saveTradeReport() {

        // todo impl
    }

    public List<TradeReport> calculateProfitLoss() {
        List<TradeReport> tradeReportList = calculateProfitLoss(tradeRecordRepo.findAll());
        return tradeReportRepo.saveAll(tradeReportList);
    }

    /* for calculating profit and loss results for matched trade pairs
    * */
    public List<TradeReport>  calculateProfitLoss(List<Trade> tradeRecords) {
        Map<String, List<Trade>> buyRecordsMap = new HashMap<>();
        List<Trade> unmatchedBuyRecords = new ArrayList<>();
        List<TradeReport> tradeReportList = new ArrayList<>();
        UUID reportId = UUID.randomUUID();

        // iterate through the tradeRecords
        for (Trade thisRecord : tradeRecords) {
            // declare and use variables for readability
            String assetName = thisRecord.getAssetName();
            String currencyPair = thisRecord.getCurrencyPair();
            String side = thisRecord.getSide();
            BigDecimal quantity = new BigDecimal(thisRecord.getQty());

            if (side.equals("buy")) {
                // add the key and an empty arrayList element to the map
                List<Trade> buyRecords = buyRecordsMap.computeIfAbsent(assetName + currencyPair, key -> new ArrayList<>());
                // add the current record into the list
                buyRecords.add(thisRecord);
            } else if (side.equals("sell")) {
                String key = assetName + currencyPair;
                List<Trade> buyRecords = buyRecordsMap.getOrDefault(key, new ArrayList<>());
                boolean matchFound = false;

                for (Trade buyRecord : buyRecords) {
                    BigDecimal buyQuantity = new BigDecimal(buyRecord.getQty());

                    // Match based on asset name, currency pair, and quantity
                    if (buyQuantity.compareTo(quantity) >= 0) {
                        BigDecimal buyAmount = new BigDecimal(buyRecord.getAmount());
                        BigDecimal sellAmount = new BigDecimal(thisRecord.getAmount());
                        BigDecimal buyFee = new BigDecimal(buyRecord.getFee());
                        BigDecimal sellFee = new BigDecimal(thisRecord.getFee());
                        BigDecimal buyTotal = buyAmount.add(buyFee).add(sellFee);
                        BigDecimal profitLoss = sellAmount.subtract(buyTotal);

                        String date = thisRecord.getDate();

                        tradeReportList.add(TradeReport.builder()
                                .id(UUID.randomUUID())
                                .reportDate(LocalDateTime.now().toString())
                                .reportId(reportId.toString())
                                .assetName(assetName)
                                .currencyPair(currencyPair)
                                .qty(quantity.toString())
                                .amount(sellAmount.toString())
                                .date(date)
                                .profitLoss(profitLoss.toString())
                                .build());

                        // Remove the matched buy record to avoid duplicate matching
                        buyRecords.remove(buyRecord);
                        matchFound = true;
                        break;
                    }
                }

                if (!matchFound) {
                    // No matching buy record found, add the sell record to unmatched records
                    unmatchedBuyRecords.add(thisRecord);
                }
            }
        }

        // Process any unmatched buy records (if applicable)
        for (Trade buyRecord : unmatchedBuyRecords) {
            String assetName = buyRecord.getAssetName();
            String currencyPair = buyRecord.getCurrencyPair();
            String date = buyRecord.getDate();
            BigDecimal amount = new BigDecimal(buyRecord.getAmount());
            BigDecimal fee = new BigDecimal(buyRecord.getFee());

            System.out.println("Unmatched Buy Record:");
            System.out.println("Asset: " + assetName);
            System.out.println("Currency Pair: " + currencyPair);
            System.out.println("Date: " + date);
            System.out.println("buyAmount: " + amount);
            System.out.println("buyFee: " + fee);
            System.out.println("side: " + buyRecord.getSide());
            System.out.println("-----------------------------------------");
        }

        tradeReportList.sort((a, b) -> a.getAssetName().compareTo(b.getAssetName()));
        return tradeReportList;
    }


    private double simpleProfitOrLoss(List<Trade> trades) {
        double totalBuyAmount = 0;
        double totalSellAmount = 0;
        double totalBuyFee = 0;
        double totalSellFee = 0;

        for (Trade trade : trades) {
            if (trade.getSide().equals("buy")) {
                totalBuyAmount += Double.parseDouble(trade.getAmount());
                totalBuyFee += Double.parseDouble(trade.getFee());
            } else if (trade.getSide().equals("sell")) {
                totalSellAmount += Double.parseDouble(trade.getAmount());
                totalSellFee += Double.parseDouble(trade.getFee());
            }
        }

        double netProfitOrLoss = totalSellAmount - totalBuyAmount - totalBuyFee - totalSellFee;
        return netProfitOrLoss;
    }

}
