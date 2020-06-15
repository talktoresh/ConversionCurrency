package com.etl.currency.conversion.Services;

import com.etl.currency.conversion.models.APIResponse;
import com.etl.currency.conversion.repo.APIResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CurrencyPersistenceService {

    @Autowired
    private APIResponseRepository responsesRepo;

    public List<APIResponse> listAll() {
        return responsesRepo.findAll();
    }

    public void save(APIResponse response) {
        responsesRepo.save(response);
    }

    public APIResponse get(long id) {
        return responsesRepo.findById(id).get();
    }

    public void delete(long id) {
        responsesRepo.deleteById(id);
    }
}
