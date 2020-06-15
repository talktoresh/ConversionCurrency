package com.etl.currency.conversion.controller;

import com.etl.currency.conversion.Services.CurrencyExtractionServiceImpl;
import com.etl.currency.conversion.Services.CurrencyPersistenceService;
import com.etl.currency.conversion.models.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/")
public class ExtractionController {

    @Autowired
    private CurrencyExtractionServiceImpl currencyExtractionService;

    @Autowired
    private CurrencyPersistenceService currencyPersistenceService;

    Logger logger = LoggerFactory.getLogger(ExtractionController.class);

    @RequestMapping("sync")
    public String extract() {

        logger.info("Extraction started!");

        /**
         * Calling the currencyExtractionService to get latest
         * currency conversion rates.
         */
        APIResponse response = currencyExtractionService.getCurrencyExtracts();

        applyCommissionToRates(response);

        persistToDB(response);

        return "success";
    }

    /**
     * Method to persist latest conversion rates to DB.
     * @param response
     */
    private void persistToDB(APIResponse response) {
        currencyPersistenceService.save(response);
    }

    /**
     * Method to update the rates with a 6% commission.
     * @param response
     */
    public void applyCommissionToRates(APIResponse response) {

        logger.info("Initial values : ");
        ratesLogging(response);

        for(Map.Entry currencySet: response.getQuotes().entrySet()) {
            Float rate = (Float) currencySet.getValue();
            Float newRate = rate + ((rate/100)*6);
            currencySet.setValue(newRate);
        }

        logger.info("After conversion : ");
        ratesLogging(response);
    }

    /**
     * Method to log a given API response.
     * @param response
     */
    private void ratesLogging(APIResponse response) {
        for(Map.Entry currencySet: response.getQuotes().entrySet()) {
            logger.info(currencySet.getKey()+" : "+ currencySet.getValue());
        }
    }
}
