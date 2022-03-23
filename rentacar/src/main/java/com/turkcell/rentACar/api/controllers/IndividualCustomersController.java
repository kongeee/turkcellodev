package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.dtos.IndividualCustomerDto;
import com.turkcell.rentACar.business.dtos.IndividualCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/individualCustomers")
public class IndividualCustomersController 
{
    private IndividualCustomerService individualCustomerService;

	public IndividualCustomersController(IndividualCustomerService individualCustomerService) 
	{
		this.individualCustomerService = individualCustomerService;
	}

    @PostMapping("/add")
	public Result add(@RequestBody CreateIndividualCustomerRequest createIndividualCustomerRequest) throws BusinessException 
	{
		return this.individualCustomerService.add(createIndividualCustomerRequest);
	}
    
	@GetMapping("/getAll")
	DataResult<List<IndividualCustomerListDto>> getAll() 
	{
		return this.individualCustomerService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<IndividualCustomerDto> getById(@RequestParam int id) throws BusinessException 
	{
		return this.individualCustomerService.getById(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateIndividualCustomerRequest updateIndividualCustomerRequest) throws BusinessException 
	{
		return this.individualCustomerService.update(updateIndividualCustomerRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteIndividualCustomerRequest deleteIndividualCustomerRequest) throws BusinessException 
	{
		return this.individualCustomerService.delete(deleteIndividualCustomerRequest);
	}
}