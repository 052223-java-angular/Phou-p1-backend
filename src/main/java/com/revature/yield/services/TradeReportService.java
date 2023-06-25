package com.revature.yield.services;

import com.revature.yield.entities.Trade;
import com.revature.yield.entities.TradeReport;
import com.revature.yield.entities.User;
import com.revature.yield.repositories.ITradeRecordRepo;
import com.revature.yield.repositories.ITradeReportRepo;
import com.revature.yield.repositories.IUserRepo;
import com.revature.yield.utils.custom_exceptions.InvalidCredentialException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

@Service
@Transactional
@AllArgsConstructor
public class TradeReportService {

    private final ITradeReportRepo tradeReportRepo;
    private final IUserRepo userRepo;
    private final ITradeRecordRepo tradeRecordRepo;

    public Set<TradeReport> getTradeReports(String userId, String reportId, String assetId, String assetName, String reportType, String currency) {

        // todo impl
        switch (reportType) {
            case "profit":
                break;
            case "loss":
                break;
            case "traded_most":
                break;
            default:
                break;
        }

        // todo impl
        return null;
    }


    public List<TradeReport> runAndSaveProfitLossReport(UUID userId) {

        Optional<User> userOpt = userRepo.findById(userId);

        // throw error
        if (userOpt.isEmpty()) {
            throw new InvalidCredentialException("Invalid userid, maybe login or try without an auth-token");
        }
        List<Trade> unmatchedBuyRecords = new ArrayList<>();
        List<TradeReport> tradeReportList = new ArrayList<>();
        UUID reportId = UUID.randomUUID();
        tradeReportList = calculateProfitLoss(tradeRecordRepo.findAllByUserId(userOpt.get().getId()), tradeReportList, unmatchedBuyRecords, reportId);

        // add user to trade report object and then save
        tradeReportList.forEach(e -> e.setUser(userOpt.get()));
        tradeReportRepo.saveAll(tradeReportList);
        return tradeReportRepo.findAll();
    }
//
//    public List<TradeReport> calculateProfitLoss(String userId) {
//        // find all records, then calculate profit and loss
//        if (true) {
//            List<Trade> unmatchedBuyRecords = new ArrayList<>();
//            List<TradeReport> tradeReportList = new ArrayList<>();
//            UUID reportId = UUID.randomUUID();
//            return calculateProfitLoss(tradeRecordRepo.findAll(), tradeReportList, unmatchedBuyRecords, reportId);
//        }
//        return tradeReportRepo.findAll();
//    }

    /* for deleting a profit loss record
    * */
    public int deleteRecordById(UUID uid) {
        return tradeReportRepo.deleteByIdReturnCount(uid);
    }

    /* for calculating profit and loss results for matched trade pairs
    * */
    public List<TradeReport>  calculateProfitLoss(List<Trade> tradeRecords,  List<TradeReport> tradeReportList, List<Trade> unmatchedBuyRecords, UUID reportId) {
        Map<String, List<Trade>> buyRecordsMap = new HashMap<>();

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
                BigDecimal remainingQuantity = quantity;

                for (Trade buyRecord : buyRecords) {
                    BigDecimal buyQuantity = new BigDecimal(buyRecord.getQty());

                    // Match based on asset name, currency pair, and quantity
                    if (buyQuantity.compareTo(BigDecimal.ZERO) > 0 && remainingQuantity.compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal buyAmount = new BigDecimal(buyRecord.getAmount());
                        BigDecimal sellAmount = new BigDecimal(thisRecord.getAmount());
                        BigDecimal buyFee = new BigDecimal(buyRecord.getFee());
                        BigDecimal sellFee = new BigDecimal(thisRecord.getFee());
                        BigDecimal buyTotal = buyAmount.add(buyFee).add(sellFee);
                        BigDecimal profitLoss = sellAmount.subtract(buyTotal);
                        BigDecimal feeTotal = buyFee.add(sellFee);
                        BigDecimal unitPrice = profitLoss.subtract(sellAmount).divide(quantity, RoundingMode.HALF_DOWN).abs();
                        BigDecimal cost = unitPrice.multiply(quantity);
                        unitPrice = cost.divide(quantity, RoundingMode.HALF_DOWN);

                        if (buyQuantity.compareTo(remainingQuantity) >= 0) {
                            // Debit sell quantity from buy quantity
                            buyRecord.setQty(buyQuantity.subtract(remainingQuantity).toString());
                            profitLoss = sellAmount.subtract(buyTotal.multiply(remainingQuantity.divide(buyQuantity, RoundingMode.HALF_DOWN)));
                            remainingQuantity = BigDecimal.ZERO;
                            String date = thisRecord.getDate();

                            tradeReportList.add(TradeReport.builder()
                                    .id(UUID.randomUUID())
                                    .reportDate(LocalDateTime.now().toString())
                                    .reportId(reportId.toString())
                                    .assetName(thisRecord.getAssetName())
                                    .currencyPair(currencyPair)
                                    .qty(quantity.toString())
                                    .amount(sellAmount.setScale(4, RoundingMode.HALF_DOWN).toString())
                                    .date(date)
                                    .fee(feeTotal.setScale(4, RoundingMode.HALF_DOWN).toString())
                                    .unitPrice(unitPrice.setScale(4, RoundingMode.HALF_DOWN).toString())
                                    .profitLoss(profitLoss.setScale(4, RoundingMode.HALF_DOWN).toString())
                                    .costBasis(cost.setScale(4, RoundingMode.HALF_DOWN).toString())
                                    .build());
                        } else {
                            // Debit entire buy quantity
                            buyRecord.setQty("0");
                            remainingQuantity = remainingQuantity.subtract(buyQuantity);
                        }

                        // Remove the matched buy record to avoid duplicate matching
                        if (remainingQuantity.compareTo(BigDecimal.ZERO) <= 0) {
                            break; // No more sell quantity remaining
                        }

                    }
                }

                if (remainingQuantity.compareTo(BigDecimal.ZERO) > 0) {
                    // Add the unmatched sell record to unmatched records
                    thisRecord.setQty(remainingQuantity.toString());
                    unmatchedBuyRecords.add(thisRecord);
                }

            }
        }

        // todo figure out how to process unmatched records
        // Process any unmatched buy records (if applicable)
//        Iterator<Trade> iterator = unmatchedBuyRecords.listIterator();
//        while(iterator.hasNext()) {
//        if (unmatchedBuyRecords.size() > 20) {
//            calculateProfitLoss(tradeRecords, tradeReportList, unmatchedBuyRecords, reportId);
//        }

//        for (Trade buyRecord : unmatchedBuyRecords) {
//            String assetName = buyRecord.getAssetName();
//            String currencyPair = buyRecord.getCurrencyPair();
//            String date = buyRecord.getDate();
//            BigDecimal amount = new BigDecimal(buyRecord.getAmount());
//            BigDecimal fee = new BigDecimal(buyRecord.getFee());

//            System.out.println("Unmatched Buy Record:");
//            System.out.println("Asset: " + assetName);
//            System.out.println("Currency Pair: " + currencyPair);
//            System.out.println("Date: " + date);
//            System.out.println("buyAmount: " + amount);
//            System.out.println("buyFee: " + fee);
//            System.out.println("side: " + buyRecord.getSide());
//            System.out.println("-----------------------------------------");
//        }

        tradeReportList.sort(Comparator.comparing(TradeReport::getAssetName));
        return tradeReportList;
    }


    private double singleAssetProfitLoss(List<Trade> trades) {
        BigDecimal totalBuyAmount = new BigDecimal(0);
        BigDecimal totalSellAmount = new BigDecimal(0);
        BigDecimal totalBuyFee = new BigDecimal(0);
        BigDecimal totalSellFee = new BigDecimal(0);

        for (Trade trade : trades) {
            BigDecimal amount = new BigDecimal(trade.getAmount());
            BigDecimal fee = new BigDecimal(trade.getFee());
            if (trade.getSide().equals("buy")) {
                totalBuyAmount = totalBuyAmount.add(amount);
                totalBuyFee = totalBuyFee.add(fee);
            } else if (trade.getSide().equals("sell")) {
                totalSellAmount = totalSellAmount.add(amount);
                totalSellFee = totalSellFee.add(fee);
            }
        }

        return totalSellAmount
                .subtract(totalBuyAmount)
                .subtract(totalBuyFee)
                .subtract(totalSellFee)
                .setScale(4, RoundingMode.HALF_DOWN)
                .doubleValue();
    }

}
