package com.revature.yield.controllers;

import com.revature.yield.services.JwtTokenService;
import com.revature.yield.services.TradeReportService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trades/reports")
@AllArgsConstructor
public class TradeReportController {

    private final JwtTokenService jwtTokenService;

    private final TradeReportService tradeReportService;

    @GetMapping
    public ResponseEntity<?> getTradeReports() {

//        String userId = jwtTokenService.extractUserId(authToken);
        String userId = "3ebceae4-c5fa-4fb1-92c5-1ae637c7cdf4";


        return ResponseEntity.status(HttpStatus.OK).body(tradeReportService.calculateProfitLoss());
    }

}
