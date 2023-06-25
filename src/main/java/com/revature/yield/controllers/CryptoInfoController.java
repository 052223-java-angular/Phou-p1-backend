package com.revature.yield.controllers;

import com.revature.yield.services.CryptoInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController()
@AllArgsConstructor
@RequestMapping("/crypto")
public class CryptoInfoController {

    private final CryptoInfoService cryptoInfoService;

    /* for retrieving crypto info about an asset
    * */
    @GetMapping("/info")
    public ResponseEntity<?> getCryptoInfo(@RequestParam("assetName") String assetName) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(cryptoInfoService.getCoinInfoById(assetName));
    }

    /* for retrieving crypto price about an asset
    * */
    @GetMapping("/price")
    public ResponseEntity<?> getCryptoSimplePrice(@RequestParam("assetNames") String[] assetNames,
                                                  @RequestParam("currencies") String[] currencies) throws IOException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(cryptoInfoService.getCryptoSimplePrice(assetNames, currencies));
    }

    /* for retrieving crypto history of an asset
     * */
    @GetMapping("/history")
    public ResponseEntity<?> getCryptoHistory(@RequestParam("assetName") String assetName,
                                              @RequestParam("date") String localDate) throws IOException {

        return ResponseEntity.status(HttpStatus.OK)
                .body(cryptoInfoService.getCryptoHistory(assetName, localDate));
    }

}
