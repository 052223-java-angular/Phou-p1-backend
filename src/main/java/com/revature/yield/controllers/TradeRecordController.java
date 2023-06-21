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


@RestController
@AllArgsConstructor
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

    /* for getting the trade record of users, all, by asset name, date range
    * */
    @GetMapping("/records")
    public ResponseEntity<?> getTradeRecords(@RequestHeader("auth_token") String authToken) {

        String userId = jwtTokenService.extractUserId(authToken);
        out.println("getTradeRecords() :: getting trade records for user_id: "+userId);

        return ResponseEntity.status(HttpStatus.OK)
                .body(tradeRecordService.getTradeRecords(userId));
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


        // todo impl
        out.println("updateTradeRecords() :: update trade report by id: "+userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tradeRecordService.updateTradeRecord(UUID.fromString(userId)));
    }

    /* for deleting records
    * */
    @DeleteMapping("/records/{tradeId}")
    public ResponseEntity<?> deleteTradeRecords(@RequestHeader("auth_token") String authToken,
                                                @RequestParam("tradeId") UUID tradeId) {

        String userId = jwtTokenService.extractUserId(authToken);
        if (userId.equals(""))
            throw new InvalidCredentialException("Invalid auth token; login and try again.");

        out.println("updateTradeRecords() :: update trade report by id: "+userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(tradeRecordService.deleteTradeRecord(tradeId));
    }

}
