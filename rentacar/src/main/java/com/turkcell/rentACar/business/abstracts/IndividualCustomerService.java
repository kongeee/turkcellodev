package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface IndividualCustomerService {
    Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest);
}
