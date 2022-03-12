package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.Result;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/individual_customers")
public class IndividualCustomersController {

    private IndividualCustomerService individualCustomerService;

	public IndividualCustomersController(IndividualCustomerService individualCustomerService) {
		this.individualCustomerService = individualCustomerService;
	}

    @PostMapping("/add")
	public Result add(@RequestBody CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException {
		return this.individualCustomerService.add(createIndividualCustomerRequest);
	}
    
}
