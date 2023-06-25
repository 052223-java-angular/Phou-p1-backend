package com.revature.yield.controllers;

import com.revature.yield.services.JwtTokenService;
import com.revature.yield.services.TradeReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/trades/reports")
@AllArgsConstructor
public class TradeReportController {

    private final JwtTokenService jwtTokenService;

    private final TradeReportService tradeReportService;

    @GetMapping("/pl")
    public ResponseEntity<?> getTradeReports(
        @RequestHeader(value = "auth_token", required = false) String authToken,
        @RequestParam(value = "report_id", required = false) UUID reportId,
        @RequestParam(value = "asset_id", required = false) String assetId,
        @RequestParam(value = "asset_name", required = false) String assetName,
        @RequestParam(value = "report_type", required = false) String reportType,
        @RequestParam(value = "currency", required = false) String currency
    ) {

        // allow calculation when no user token is present
        if (authToken == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(tradeReportService.calculateProfitLoss());
        }


        return ResponseEntity.status(HttpStatus.OK)
                .body(tradeReportService.saveTradeReport(
                        jwtTokenService.extractUserId(authToken),
                        "", "", "", "", ""));
    }

}
