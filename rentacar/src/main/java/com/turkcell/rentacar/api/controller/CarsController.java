package com.turkcell.rentacar.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.dtos.CarListDto;

import com.turkcell.rentacar.business.requests.CreateCarRequest;

import com.turkcell.rentacar.business.requests.UpdateCarRequest;

import com.turkcell.rentacar.core.exceptions.BusinessException;

@RestController
@RequestMapping("/api/cars")

public class CarsController {
	private CarService carService;

	public CarsController(CarService carService) {
		
		this.carService = carService;
	}
	
	@GetMapping("/getall")
	public List<CarListDto> getAll(){
		return this.carService.getAll();
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateCarRequest createcarRequest) throws BusinessException {

		this.carService.add(createcarRequest);
	}
	
	@GetMapping("/getbyid")
	public CarDto getById(@RequestParam(required = true) int carId){
		return this.carService.getById(carId);
	}
	
	@PostMapping("/update")
	public void update(@RequestBody UpdateCarRequest updatecarRequest) throws BusinessException {
		this.carService.update(updatecarRequest);
	}
	
	@PostMapping("/deletebyid")
	public void deleteById(@RequestBody int carId) {
		this.carService.deleteById(carId);
	}

	
}
