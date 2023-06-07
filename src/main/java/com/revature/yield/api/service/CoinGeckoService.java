package com.revature.yield.api.service;

import com.revature.yield.api.configs.CoinHistoryParam;
import com.revature.yield.api.configs.Param;
import com.revature.yield.api.dtos.CoinHistoryDTO;
import com.revature.yield.api.dtos.PriceHistoryDTO;
import com.revature.yield.utils.custom_exceptions.HttpException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.revature.yield.utils.DataTypeUtil.toDouble;
import static com.revature.yield.utils.json.JsonUtil.*;

@Service
public class CoinGeckoService {

//    https://api.coingecko.com/api/v3/simple/price?ids=Bitcoin&vs_currencies=usd&include_market_cap=true&include_24hr_vol=true&include_24hr_change=true&include_last_updated_at=true&precision=full

    /* the base uri for Coingecko's api services / resource
    * */
    private final String BASE_URI = "https://api.coingecko.com/api/v3/";

    /* gets the HTTP resource by creating a URL instance using a Base_uri and param resource url and opening a connection.
    *
    * @param paramConfigObj a configuration object containing the fields and values in which the api resource accepts and recognizes as valid
    * @return an InputStream instance containing the resource when successful or throws an HttpException on failures
    * @exception IOException thrown when connection fails to open
    * @exception HttpException thrown when Http request status code is not 200
    * */
    private <T extends Param> InputStream getApiResource(T paramConfigObj) throws IOException {
        // create url and open connection
        URL url = new URL(BASE_URI + paramConfigObj.toString());
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        // set http header - does not require an API key
        httpURLConnection.setRequestMethod("GET");

        // make the request and validate the response for a valid status code
        int resCode = httpURLConnection.getResponseCode();
        if (resCode != 200) {
            throw new HttpException("HTTP Response code: " + resCode);
        }

        // request was valid, retrieve the response as an input stream
        return httpURLConnection.getInputStream();
    }

    /* api request to get the simple price of assets listed within the param-config-obj
    *
    * @param paramConfigObj a configuration object containing the fields and values in which the api resource accepts and recognizes as valid
    * @return a list of the price history instances
    *  */
    public <T extends Param> List<PriceHistoryDTO> getSimplePrice(T paramConfigObj) throws IOException {

        // transform response into desired format, i.e. string, json, etc
        JSONObject jsonObj = toJsonObject(getApiResource(paramConfigObj));

        // extract the data array from the json object
        List<PriceHistoryDTO> phList = new ArrayList<>();

        // iterate through every key and create an entry
        for (String key : jsonObj.keySet()) {

            // extract all the keys and value types from the current jsonObject by using the key
            Map<String, Object> mKeyPairs = extractKeyAndJsonType(jsonObj.getJSONObject(key), new HashMap<>(), true);

            // add the top-level key as the id and map all other values into String format
            mKeyPairs.put("id", key);
            Map<String, String> keyValuePairs = mapObjectsToString(mKeyPairs);

            // map the key & value pairs using the toDto helper method
            phList.add(toPriceHistoryFromMap(keyValuePairs));

        }
        return phList;

    }

    public <T extends Param> CoinHistoryDTO getCoinHistory(T paramConfigObj) throws IOException {

        // transform response into desired format, i.e. string, json, etc
        JSONObject jsonObj = toJsonObject(getApiResource(paramConfigObj));

        // extract all the keys and value types from the current jsonObject by using the key
        Map<String, Object> mKeyPairs = extractKeyAndJsonType(jsonObj, new HashMap<>(), true);

        Map<String, String> keyValuePairs = mapObjectsToString(mKeyPairs);
        keyValuePairs.put("date_time", ((CoinHistoryParam) paramConfigObj).formatDateTime());

        return toCoinHistoryFromMap(keyValuePairs);

    }

    /*==========   HELPER METHODS FOR CREATING OBJECTS  =================*/


    /* Retrieves the current price history
    * */
    private PriceHistoryDTO toPriceHistoryFromMap(Map<String, String> mapEntry) {
        return new PriceHistoryDTO.Builder(mapEntry.get("id"))
                .withPrice(toDouble(mapEntry.get("usd")))
                .withMarketCap(toDouble(mapEntry.get("usd_market_cap")))
                .withTotalVolume24Hr(toDouble(mapEntry.get("usd_24h_vol")))
                .withTotalVolume24HrChange(toDouble(mapEntry.get("usd_24h_change")))
                .withDateAndTime(mapEntry.get("last_updated_at"))
                .build();
    }

    /* Retrieves the historical price history
    * */
    private CoinHistoryDTO toCoinHistoryFromMap(Map<String, String> mapEntry) {
        return new CoinHistoryDTO.Builder(mapEntry.get("id"))
                .withSymbol(mapEntry.get("symbol"))
                .withName(mapEntry.get("name"))
                .withImages(mapEntry.get("small") + "," + mapEntry.get("thumb"))
                .withUsdPrice(toDouble(mapEntry.get("usd")))
                .withBtcPrice(toDouble(mapEntry.get("btc")))
                .withEthPrice(toDouble(mapEntry.get("eth")))
                .withBnbPrice(toDouble(mapEntry.get("bnb")))
                .withDateTime(mapEntry.get("date_time"))
                .build();
    }
}
