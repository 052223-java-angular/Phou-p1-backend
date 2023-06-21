package com.revature.yield.services;

import com.revature.yield.dtos.request.SaveTradeRequest;
import com.revature.yield.entities.Trade;
import com.revature.yield.entities.User;
import com.revature.yield.repositories.ITradeRecordRepository;
import com.revature.yield.repositories.IUserRepository;
import com.revature.yield.utils.custom_exceptions.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TradeRecordService {
    private final ITradeRecordRepository tradeRecordRepository;
    private final IUserRepository userRepository;

    /* creates trade records from the save trade request
    * */
    public List<Trade> saveTradeRecords(List<SaveTradeRequest> saveTradeRequest, UUID userId) {

        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("User account not found, unable to save trade records."));

        UUID reportId = UUID.randomUUID();  // create a unique report id
        List<Trade> tradeList = saveTradeRequest
                .stream()
                .map(trade -> fromSaveTradeReqToTrade(trade, foundUser, reportId, trade.getSide()))
                .toList();
        return tradeRecordRepository.saveAll(tradeList);
    }

    /* gets all trade records matching the user id
    * */
    public List<Trade> getTradeRecords(String userId) {
        return tradeRecordRepository.findAllByUserId(UUID.fromString(userId));
    }

    /* gets all trade report ids for the matched user id
     * */
    public Set<String> getTradeRecordsReportIds(UUID userId) {
        return tradeRecordRepository.findReportIdsByUserId(userRepository.findById(userId)
                    .orElseThrow(() -> new UnauthorizedException("User account not found, unable to save trade records."))
                    .getId());
    }

    public Set<Trade> updateTradeRecord(UUID userId ) {


//        return tradeRecordRepository.updateTradeRecord();
        return null;
    }

    public int deleteTradeRecord(UUID tradeRecordId) {
        tradeRecordRepository.deleteById(tradeRecordId)
        return 1;
    }




    /* used for translating request fields into a Trade java object
    * */
    private Trade fromSaveTradeReqToTrade(SaveTradeRequest saveTradeRequest, User foundUser, UUID reportId, String side) {
        return Trade.builder()
                .id(UUID.randomUUID())
                .reportDate(LocalDateTime.now().toString())
                .reportId(reportId.toString())
                .buyDate(side.equalsIgnoreCase("buy") ? saveTradeRequest.getDate() : LocalDateTime.MIN.toString())
                .sellDate(side.equalsIgnoreCase("buy") ? LocalDateTime.MIN.toString() : saveTradeRequest.getDate())
                .buyValue(side.equalsIgnoreCase("buy") ? saveTradeRequest.getAmountPaid() : "0")
                .sellValue(side.equalsIgnoreCase("buy") ? "0" : saveTradeRequest.getAmountPaid())
                .buyFee(side.equalsIgnoreCase("buy") ? saveTradeRequest.getFee() : "0")
                .sellFee(side.equalsIgnoreCase("buy") ? "0" : saveTradeRequest.getFee())
                .boughtQty(side.equalsIgnoreCase("buy") ? saveTradeRequest.getQty() : "0")
                .soldQty(side.equalsIgnoreCase("buy") ? "0" : saveTradeRequest.getQty())
                .assetName(saveTradeRequest.getAsset())
                .orderId(saveTradeRequest.getOrderId())
                .unitPrice(saveTradeRequest.getUnitPrice())
                .currencyPair(saveTradeRequest.getCurrencyPair())
                .user(foundUser)
                .build();
    }
}
