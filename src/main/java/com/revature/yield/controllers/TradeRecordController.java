package com.revature.yield.controllers;

import com.revature.yield.dtos.request.SaveTradeRequest;
import com.revature.yield.dtos.request.UpdateTradeRequest;
import com.revature.yield.services.JwtTokenService;
import com.revature.yield.services.TradeRecordService;
import com.revature.yield.utils.custom_exceptions.InvalidCredentialException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.lang.System.out;

@AllArgsConstructor
@RestController
@RequestMapping("/trades")
public class TradeRecordController {

    private final TradeRecordService tradeRecordService;

    private final JwtTokenService jwtTokenService;

    @PostMapping("/records")
    public ResponseEntity<?> saveTradeRecords(@RequestHeader("auth_token") String authToken,
                                            @RequestBody List<SaveTradeRequest> saveTradesRequest) {

        String userId = jwtTokenService.extractUserId(authToken);
        out.println("saveTradeRecords() :: saving trade records for userId: "+userId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tradeRecordService.saveTradeRecords(saveTradesRequest, UUID.fromString(userId)));
    }

    /* for getting the trade record of users all or
    * */
    @GetMapping("/records")
    public ResponseEntity<?> getTradeRecords(@RequestHeader("auth_token") String authToken,
                                             @RequestParam(value = "recordId", required = false) String recordId) {


        String userId = jwtTokenService.extractUserId(authToken);
        out.println("getTradeRecords() :: getting trade records for user_id: "+userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(tradeRecordService.getTradeRecords(userId, recordId));
    }

    /* for getting the trade record by asset name, price, profit and loss by date range
     * */
    @GetMapping("/records/search")
    public ResponseEntity<?> getTradeRecordsBy(@RequestHeader("auth_token") String authToken,
                                             @RequestParam(value = "profit", required = false) String profit,
                                             @RequestParam(value = "loss", required = false) String loss,
                                             @RequestParam(value = "name", required = false) String name,
                                             @RequestParam(value = "price", required = false) String price,
                                             @RequestParam(value = "from", required = false) String from,
                                             @RequestParam(value = "to", required = false) String to) {

        // todo implement switch logic


        String userId = jwtTokenService.extractUserId(authToken);
        out.println("getTradeRecords() :: getting trade records for user_id: "+userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(tradeRecordService.getTradeRecords(userId, ""));
    }

    /* for getting reports id
     * */
    @GetMapping("/records/reports")
    public ResponseEntity<?> getTradeRecordReportIds(@RequestHeader("auth_token") String authToken) {

        String userId = jwtTokenService.extractUserId(authToken);

        out.println("getTradeRecordReportIds() :: getting trade report ids for user_id: "+userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tradeRecordService.getTradeRecordsReportIds(UUID.fromString(userId)));
    }

    /* for updating records - allowing user to modify and rerun reports with applied adjustments
    * */
    @PutMapping("/records")
    public ResponseEntity<?> updateTradeRecords(@RequestHeader("auth_token") String authToken,
                                                @RequestBody UpdateTradeRequest updateTradeRequest) {

        String userId = jwtTokenService.extractUserId(authToken);

        out.println("updateTradeRecords() :: update trade record by id: "+userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tradeRecordService.updateTradeRecord(UUID.fromString(userId), updateTradeRequest));
    }

    /* for deleting records
    * */
    @DeleteMapping("/records")
    public ResponseEntity<?> deleteTradeRecords(@RequestHeader("auth_token") String authToken,
                                                @RequestParam("trade_record_id") String tradeRecordId) {

        UUID tradeId = UUID.fromString(tradeRecordId);

        // extract user id
        String userId = jwtTokenService.extractUserId(authToken);
        if (userId.equals(""))
            throw new InvalidCredentialException("Invalid auth token; login and try again.");

        out.println("updateTradeRecords() :: delete trade record by id: "+tradeRecordId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tradeRecordService.deleteTradeRecordById(tradeId));
    }

}
