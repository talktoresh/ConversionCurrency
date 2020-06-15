package com.etl.currency.conversion.controller;

import com.etl.currency.conversion.Services.CurrencyExtractionServiceImpl;
import com.etl.currency.conversion.Services.CurrencyPersistenceService;
import com.etl.currency.conversion.models.APIResponse;
import net.minidev.json.JSONUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExtractionControllerTest {

    @MockBean
    private CurrencyExtractionServiceImpl currencyExtractionService;

    @Autowired
    private ExtractionController extractionController;

    @Autowired
    private CurrencyPersistenceService currencyPersistenceService;

    @Test
    public void applyCommissionToRatesTest() {
        System.out.println("Extraction test in progress!");

        APIResponse mockResponse = getMockAPIResponse();

        when(currencyExtractionService.getCurrencyExtracts()).thenReturn(mockResponse);

        extractionController.applyCommissionToRates(mockResponse);

        assertEquals(4,mockResponse.getQuotes().size());

        Float usdRate = mockResponse.getQuotes().get("USDUSD");

        assertEquals(1.06,(float)usdRate,0.0001);

    }


    @Test
    @Transactional
    public void persistToDBTest() {

        assertEquals(0,currencyPersistenceService.listAll().size());

        APIResponse mockResponse = getMockAPIResponse();
        extractionController.applyCommissionToRates(mockResponse);
        currencyPersistenceService.save(mockResponse);

        assertEquals(1,currencyPersistenceService.listAll().size());
        assertEquals(4,currencyPersistenceService.get(1).getQuotes().size());
    }

    public APIResponse getMockAPIResponse() {
        APIResponse response = new APIResponse();
        response.setId(new Long(1));
        response.setSource("USD");
        response.setTimestamp((long)15235689);

        Map<String,Float> quotes = new HashMap<String, Float>();
        quotes.put("USDUSD", (float) 1);
        quotes.put("USDAUD",(float)1.557604);
        quotes.put("USDCAD",(float)1.42635);
        quotes.put("USDMXN",(float)24.60260);

        response.setQuotes(quotes);

        return response;
    }
}
