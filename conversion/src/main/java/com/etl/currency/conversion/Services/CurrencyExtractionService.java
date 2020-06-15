package com.etl.currency.conversion.Services;

import com.etl.currency.conversion.models.APIResponse;
import org.springframework.stereotype.Service;

public interface CurrencyExtractionService {

    public APIResponse getCurrencyExtracts();
}
