package com.revature.yield.services;

import com.revature.yield.dtos.request.Request;
import com.revature.yield.dtos.request.SaveTradeRequest;
import com.revature.yield.dtos.request.UpdateTradeRequest;
import com.revature.yield.entities.Trade;
import com.revature.yield.entities.User;
import com.revature.yield.repositories.ITradeRecordRepo;
import com.revature.yield.repositories.IUserRepo;
import com.revature.yield.utils.custom_exceptions.UnauthorizedException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.lang.System.out;

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

        out.println("Mapped entities ");
        tradeList.forEach(out::println);

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

        return tradeRecordRepository.saveAndFlush(
                fromSaveTradeReqToTrade(updateTradeRequest, foundUser, updateTradeRequest.getId(), ""));
    }

    // delete a trade record by its id and then returns the count
    public int deleteTradeRecordById(UUID id) {
        return tradeRecordRepository.deleteByIdReturnCount(id);
    }


    /* used for translating request fields into a Trade java object
    * */
    private <T extends Request> Trade fromSaveTradeReqToTrade(T tradeRequest, User foundUser, UUID reportId, String side) {

        if (tradeRequest instanceof SaveTradeRequest) {
            return Trade.builder()
                    .reportDate(LocalDateTime.now().toString())
                    .reportId(reportId.toString())
                    .assetName(((SaveTradeRequest) tradeRequest).getAssetName())
                    .currencyPair(((SaveTradeRequest) tradeRequest).getCurrencyPair())
                    .side(((SaveTradeRequest) tradeRequest).getSide())
                    .addDate(LocalDateTime.now().toString())
                    .date(((SaveTradeRequest) tradeRequest).getDate())
                    .amount(((SaveTradeRequest) tradeRequest).getAmount())
                    .fee(((SaveTradeRequest) tradeRequest).getFee())
                    .qty(((SaveTradeRequest) tradeRequest).getQty())
                    .orderId(((SaveTradeRequest) tradeRequest).getOrderId())
                    .unitPrice(((SaveTradeRequest) tradeRequest).getUnitPrice())
                    .user(foundUser)
                    .build();
        }

//        if (tradeRequest instanceof UpdateTradeRequest) {
        return Trade.builder()
                .id(((UpdateTradeRequest) tradeRequest).getId())
                .reportDate(LocalDateTime.now().toString())
                .reportId(reportId.toString())
                .assetName(((UpdateTradeRequest) tradeRequest).getAssetName())
                .currencyPair(((UpdateTradeRequest) tradeRequest).getCurrencyPair())
                .side(((UpdateTradeRequest) tradeRequest).getBoughtQty() != "0" ? "buy" : "sell")
                .addDate(LocalDateTime.now().toString())
                .date(((UpdateTradeRequest) tradeRequest).getBuyDate())
                .amount(((UpdateTradeRequest) tradeRequest).getBuyValue() != "0" ? ((UpdateTradeRequest) tradeRequest).getBuyValue() : ((UpdateTradeRequest) tradeRequest).getSellValue()  )
                .fee(((UpdateTradeRequest) tradeRequest).getBuyFee() != "0" ? ((UpdateTradeRequest) tradeRequest).getBuyFee() : ((UpdateTradeRequest) tradeRequest).getSellFee())
                .qty(((UpdateTradeRequest) tradeRequest).getBoughtQty() != "0" ? ((UpdateTradeRequest) tradeRequest).getBoughtQty() : ((UpdateTradeRequest) tradeRequest).getSoldQty())
                .orderId(((UpdateTradeRequest) tradeRequest).getOrderId())
                .unitPrice(((UpdateTradeRequest) tradeRequest).getUnitPrice())
                .user(foundUser)
                .build();
//        }

    }

}
