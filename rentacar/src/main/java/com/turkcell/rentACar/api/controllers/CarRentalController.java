package com.turkcell.rentACar.api.controllers;

import java.util.List;

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
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/carRental")
public class CarRentalController 
{
	private CarRentalService carRentalService;

	public CarRentalController(CarRentalService carRentalService) 
	{
		this.carRentalService = carRentalService;
	}

	@GetMapping("/getAll")
	DataResult<List<CarRentalDto>> getAll() 
	{
		return carRentalService.getAll();
	}

	@PostMapping("/add")
	Result add(@RequestBody CreateCarRentalRequest createCarRentalRequest)  throws BusinessException
	{
		return carRentalService.add(createCarRentalRequest);
	}

	@PutMapping("/update")
	Result update(@RequestBody UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException
	{
		return carRentalService.update(updateCarRentalRequest);
	}

	@DeleteMapping("/delete")
	Result delete(@RequestBody DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException
	{
		return carRentalService.delete(deleteCarRentalRequest);
	}

	@GetMapping("/getByCarId")
	DataResult<List<CarRentalListDto>> getByCarId(@RequestParam int id) throws BusinessException 
	{
		return carRentalService.getByCarId(id);
	}

	@PostMapping("/calculatePrice")
	DataResult<Double> calculatePrice(@RequestBody CreateCarRentalRequest createCarRentalRequest) throws BusinessException
	{
		return carRentalService.calculatePrice(createCarRentalRequest);
	}

}
