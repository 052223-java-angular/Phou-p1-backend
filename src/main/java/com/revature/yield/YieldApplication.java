package com.revature.yield;

import com.revature.yield.api.configs.SimplePriceParam;
import com.revature.yield.api.dtos.PriceHistoryDTO;
import com.revature.yield.api.service.CoinGeckoService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;

import static java.lang.System.out;

@SpringBootApplication
public class YieldApplication {

	public static void main(String[] args) {
		SpringApplication.run(YieldApplication.class, args);
		CoinGeckoService cgService = new CoinGeckoService();

		try {

			List<PriceHistoryDTO> priceHistoryList = cgService.getSimplePrice(new SimplePriceParam(new String[]{"near", "bitcoin"}, new String[]{"usd"}));
			priceHistoryList.forEach(entry -> out.println(entry.toString()));

		} catch (IOException e) {
			out.println(e.getLocalizedMessage());
		}

	}

}
