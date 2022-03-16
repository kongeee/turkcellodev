package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentACar.business.dtos.CorporateCustomerListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCorporateCustomerRequest;
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
@RequestMapping("/api/corporate_customers")
public class CorporateCustomersController 
{
    private CorporateCustomerService corporateCustomerService;

	public CorporateCustomersController(CorporateCustomerService corporateCustomerService)
    {
		this.corporateCustomerService = corporateCustomerService;
	}

    @PostMapping("/add")
	public Result add(@RequestBody CreateCorporateCustomerRequest createCorporateCustomerRequest) throws BusinessException 
    {
		return this.corporateCustomerService.add(createCorporateCustomerRequest);
	}
    
	@GetMapping("/getAll")
	DataResult<List<CorporateCustomerListDto>> getAll() 
    {
		return this.corporateCustomerService.getAll();
	}

	@GetMapping("/getById")
	public DataResult<CorporateCustomerDto> getById(@RequestParam int id) throws BusinessException 
    {
		return this.corporateCustomerService.getById(id);
	}

	@PutMapping("/update")
	public Result update(@RequestBody UpdateCorporateCustomerRequest updateCorporateCustomerRequest) throws BusinessException 
    {
		return this.corporateCustomerService.update(updateCorporateCustomerRequest);
	}

	@DeleteMapping("/delete")
	public Result delete(@RequestBody DeleteCorporateCustomerRequest deleteCorporateCustomerRequest) throws BusinessException 
    {
		return this.corporateCustomerService.delete(deleteCorporateCustomerRequest);
	}
}