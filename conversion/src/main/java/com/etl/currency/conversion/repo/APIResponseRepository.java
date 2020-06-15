package com.etl.currency.conversion.repo;

import com.etl.currency.conversion.models.APIResponse;
import org.springframework.data.jpa.repository.JpaRepository;

public interface APIResponseRepository extends JpaRepository<APIResponse, Long> {

}
