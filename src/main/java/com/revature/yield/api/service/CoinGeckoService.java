package com.revature.yield.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.yield.api.dtos.Param;
import com.revature.yield.api.dtos.coin.id.JCoinDTO;
import com.revature.yield.api.dtos.coin.history.JHistoryCoinDTO;
import com.revature.yield.api.dtos.coin.simple_price.JPriceSimpleDTO;
import com.revature.yield.utils.custom_exceptions.HttpException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class CoinGeckoService {

    /* the base uri for Coingecko's api services / resource
    * */
    private static final String BASE_URI = "https://api.coingecko.com/api/v3/";

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

    /* api request to get the simple price of coin assets from /simple/price
    *
    * @param paramConfigObj a configuration object containing the fields and values in which the api resource accepts and recognizes as valid
    * @param objectMapper a mapper for mapping input-stream into java objects
    * @return a list of the price history instances
    *  */
    public <T extends Param> JPriceSimpleDTO getSimplePrice(T paramConfigObj, ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(getApiResource(paramConfigObj), JPriceSimpleDTO.class);
    }

    /* api request to get the price of an asset from resource endpoint /coins/{id}
     *
     * @param paramConfigObj a configuration object containing the fields and values in which the api resource accepts and recognizes as valid
     * @param objectMapper a mapper for mapping input-stream into java objects
     * @return the coins price history
     *  */
    public <T extends Param> JCoinDTO getCoinById(T paramConfigObj, ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(getApiResource(paramConfigObj), JCoinDTO.class);
    }

    /* api request to get the historical price of an asset from resource /coins/{id}/history
     *
     * @param paramConfigObj a configuration object containing the fields and values in which the api resource accepts and recognizes as valid
     * @param objectMapper a mapper for mapping input-stream into java objects
     * @return the coins price history
     *  */
    public <T extends Param> JHistoryCoinDTO getCoinHistory(T paramConfigObj, ObjectMapper objectMapper) throws IOException {
        return objectMapper.readValue(getApiResource(paramConfigObj), JHistoryCoinDTO.class);
    }

}
