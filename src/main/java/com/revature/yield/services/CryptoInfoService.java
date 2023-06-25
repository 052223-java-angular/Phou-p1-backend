package com.revature.yield.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.yield.api.dtos.coin.history.CoinHistoryParam;
import com.revature.yield.api.dtos.coin.history.JHistoryCoinDTO;
import com.revature.yield.api.dtos.coin.id.CoinParam;
import com.revature.yield.api.dtos.coin.id.JCoinDTO;
import com.revature.yield.api.dtos.coin.simple_price.JPriceSimpleDTO;
import com.revature.yield.api.dtos.coin.simple_price.SimplePriceParam;
import com.revature.yield.api.service.CoinGeckoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.lang.System.out;

@Service
@AllArgsConstructor
public class CryptoInfoService {

    private final CoinGeckoService coinGeckoService;

    public JCoinDTO getCoinInfoById(String assetName) throws IOException {

        // todo conditional impl == check backend db for info first, if unavailable, then pull from external api
        // todo filter data into desired format
        CoinParam param = new CoinParam(assetName);
        param.setLocalization(false);
        JCoinDTO result = coinGeckoService.getCoinById(param, new ObjectMapper());
        out.println(result);
        return result;
    }

    public JPriceSimpleDTO getCryptoSimplePrice(String[] assetNames, String[] currencies) throws IOException {

        // todo conditional impl == check backend db for info first, if unavailable, then pull from external api
        // todo filter data into desired format
        SimplePriceParam param = new SimplePriceParam(assetNames, currencies);
        JPriceSimpleDTO result = coinGeckoService.getSimplePrice(param, new ObjectMapper());
        out.println(result);
        return result;
    }

    public JHistoryCoinDTO getCryptoHistory(String assetName, String localDate) throws IOException {

        // todo conditional impl == check backend db for info first, if unavailable, then pull from external api
        // todo filter data into desired format
        CoinHistoryParam param = new CoinHistoryParam(assetName, LocalDate.parse(localDate, DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        JHistoryCoinDTO result = coinGeckoService.getCoinHistory(param, new ObjectMapper());
        out.println(result);
        return result;
    }

}
