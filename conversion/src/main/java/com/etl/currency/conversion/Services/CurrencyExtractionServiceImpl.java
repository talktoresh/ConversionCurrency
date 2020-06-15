package com.etl.currency.conversion.Services;

import com.etl.currency.conversion.models.APIResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CurrencyExtractionServiceImpl implements CurrencyExtractionService {

    final String uri = "http://api.currencylayer.com/live?access_key=66e85b78a333b36ad12&currencies=USD,AUD,CAD,PLN,MXN&format=1";

    @Override
    public APIResponse getCurrencyExtracts() {

        RestTemplate restTemplate = new RestTemplate();
        APIResponse result = restTemplate.getForObject(uri, APIResponse.class);

        System.out.println(result.isSuccess());

        return result;

    }
}
