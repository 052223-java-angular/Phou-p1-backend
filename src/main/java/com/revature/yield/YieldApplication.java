package com.revature.yield;

import com.revature.yield.api.service.CoinGeckoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication()
public class YieldApplication {

	public static void main(String[] args) {
		SpringApplication.run(YieldApplication.class, args);
		CoinGeckoService cgService = new CoinGeckoService();

//		try {
//
////			JPriceSimpleDTO priceHistoryList = cgService.getSimplePrice(
////					new SimplePriceParam(new String[]{"near", "bitcoin"}, new String[]{"usd"}),
////					new ObjectMapper());
////			priceHistoryList.entrySet().forEach(out::println);
//
////			JHistoryCoinDTO coinHistoryDTO = cgService.getCoinHistory(
////					new CoinHistoryParam("near", LocalDateTime.of(2020, 11, 11, 2, 44, 0)),
////					new ObjectMapper());
////			out.println(coinHistoryDTO.toString());
//
////			JCoinDTO jCoinDTO = cgService.getCoinById(new CoinParam("near"), new ObjectMapper());
////			out.println(jCoinDTO);
//
//		} catch (IOException e) {
//			out.println(e.getLocalizedMessage());
//		}

	}

}
