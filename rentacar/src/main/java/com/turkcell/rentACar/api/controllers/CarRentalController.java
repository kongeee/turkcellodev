package com.turkcell.rentACar.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.dtos.CarRentalDto;
import com.turkcell.rentACar.business.dtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carRental")
public class CarRentalController 
{
	private CarRentalService carRentalService;

	@Autowired
	public CarRentalController(CarRentalService carRentalService) 
	{
		this.carRentalService = carRentalService;
	}

	@GetMapping("/getAll")
	DataResult<List<CarRentalListDto>> getAll() 
	{
		return carRentalService.getAll();
	}

	@PostMapping("/rentForIndividualCustomer")
	Result add(@RequestBody CreateCarRentalForIndividualCustomerRequest carRentalForIndividualCustomerRequest)  throws BusinessException
	{
		return carRentalService.rentForIndividualCustomer(carRentalForIndividualCustomerRequest);
	}

	@PostMapping("/rentForCorporateCustomer")
	Result add(@RequestBody CreateCarRentalForCorporateCustomerRequest createCarRentalForCorporateCustomerRequest)  throws BusinessException
	{
		return carRentalService.rentForCorporateCustomer(createCarRentalForCorporateCustomerRequest);
	}

	@PutMapping("/updateForIndividualCustomer")
	Result update(@RequestBody UpdateCarRentalForIndividualCustomerRequest updateCarRentalForIndividualCustomerRequest) throws BusinessException
	{
		return carRentalService.updateForIndividualCustomer(updateCarRentalForIndividualCustomerRequest);
	}

	@PutMapping("/updateForCorporateCustomer")
	Result update(@RequestBody UpdateCarRentalForCorporateCustomerRequest updateCarRentalForCorporateCustomerRequest) throws BusinessException
	{
		return carRentalService.updateForCorporateCustomer(updateCarRentalForCorporateCustomerRequest);
	}

	@DeleteMapping("/deleteForCorporateCustomer")
	Result delete(@RequestBody DeleteCarRentalForCorporateCustomerRequest deleteCarRentalForCorporateCustomerRequest) throws BusinessException
	{
		return carRentalService.deleteForCorporateCustomer(deleteCarRentalForCorporateCustomerRequest);
	}

	@DeleteMapping("/deleteIndividualCustomer")
	Result delete(@RequestBody DeleteCarRentalForIndividualCustomerRequest deleteCarRentalForIndividualCustomerRequest) throws BusinessException
	{
		return carRentalService.deleteForIndividualCustomer(deleteCarRentalForIndividualCustomerRequest);
	}

	@GetMapping("/getByCarId")
	DataResult<List<CarRentalListDto>> getByCarId(@RequestParam int id) throws BusinessException 
	{
		return carRentalService.getByCarId(id);
	}
}
