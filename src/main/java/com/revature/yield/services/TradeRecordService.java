package com.revature.yield.services;

import com.revature.yield.dtos.request.SaveTradeRequest;
import com.revature.yield.dtos.request.UpdateTradeRequest;
import com.revature.yield.entities.Trade;
import com.revature.yield.entities.User;
import com.revature.yield.repositories.ITradeRecordRepo;
import com.revature.yield.repositories.IUserRepo;
import com.revature.yield.utils.custom_exceptions.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class TradeRecordService {
    private final ITradeRecordRepo tradeRecordRepository;
    private final IUserRepo userRepository;

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

        // todo call service to run report


        return tradeRecordRepository.saveAll(tradeList);
    }

    /* gets all trade records matching the user id
    * */
    public List<Trade> getTradeRecords(String userId, String recordId) {
        if (recordId == null)
            return tradeRecordRepository.findAllByUserId(UUID.fromString(userId));
        return tradeRecordRepository.findAllByIdAndUserId(UUID.fromString(recordId), UUID.fromString(userId));
    }


    /* gets all trade report ids for the matched user id
     * */
    public Set<String> getTradeRecordsReportIds(UUID userId) {
        return tradeRecordRepository.findReportIdsByUserId(userRepository.findById(userId)
                    .orElseThrow(() -> new UnauthorizedException("User account not found, unable to save trade records."))
                    .getId());
    }

    public Trade updateTradeRecord(UUID userId, UpdateTradeRequest updateTradeRequest) {
        User foundUser = userRepository.findById(userId)
                .orElseThrow(() -> new UnauthorizedException("User account not found, unable to save trade records."));

        updateTradeRequest.setUser(foundUser);
        Trade trade = new Trade();
        BeanUtils.copyProperties(updateTradeRequest, trade);
        return tradeRecordRepository.saveAndFlush(trade);
    }

    // delete a trade record by its id and then returns the count
    public int deleteTradeRecordById(UUID id) {
        return tradeRecordRepository.deleteByIdReturnCount(id);
    }


    /* used for translating request fields into a Trade java object
    * */
    private Trade fromSaveTradeReqToTrade(SaveTradeRequest tradeRequest, User foundUser, UUID reportId, String side) {
        return Trade.builder()
                .id(UUID.randomUUID())
                .reportDate(LocalDateTime.now().toString())
                .reportId(reportId.toString())
                .assetName(tradeRequest.getAsset())
                .currencyPair(tradeRequest.getCurrencyPair())
                .side(tradeRequest.getSide())
                .addDate(LocalDateTime.now().toString())
                .date( tradeRequest.getDate())
                .amount(tradeRequest.getAmountPaid())
                .fee(tradeRequest.getFee())
                .qty(tradeRequest.getQty())
                .orderId(tradeRequest.getOrderId())
                .unitPrice(tradeRequest.getUnitPrice())
                .user(foundUser)
                .build();

//        return Trade.builder()
//                .id(UUID.randomUUID())
//                .reportDate(LocalDateTime.now().toString())
//                .reportId(reportId.toString())
//                .addDate(LocalDateTime.now().toString())
//                .buyDate(side.equalsIgnoreCase("buy") ? tradeRequest.getDate() : LocalDateTime.MIN.toString())
//                .sellDate(side.equalsIgnoreCase("buy") ? LocalDateTime.MIN.toString() : tradeRequest.getDate())
//                .buyAmount(side.equalsIgnoreCase("buy") ? tradeRequest.getAmountPaid() : "0")
//                .sellAmount(side.equalsIgnoreCase("buy") ? "0" : tradeRequest.getAmountPaid())
//                .buyFee(side.equalsIgnoreCase("buy") ? tradeRequest.getFee() : "0")
//                .sellFee(side.equalsIgnoreCase("buy") ? "0" : tradeRequest.getFee())
//                .boughtQty(side.equalsIgnoreCase("buy") ? tradeRequest.getQty() : "0")
//                .soldQty(side.equalsIgnoreCase("buy") ? "0" : tradeRequest.getQty())
//                .assetName(tradeRequest.getAsset())
//                .orderId(tradeRequest.getOrderId())
//                .unitPrice(tradeRequest.getUnitPrice())
//                .currencyPair(tradeRequest.getCurrencyPair())
//                .side(tradeRequest.getSide())
//                .user(foundUser)
//                .build();
    }
}
