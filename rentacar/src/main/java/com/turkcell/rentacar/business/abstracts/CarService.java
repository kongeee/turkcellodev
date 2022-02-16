package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.UpdateCarRequest;


public interface CarService {
	List<CarListDto> getAll();
	void add(CreateCarRequest createCarRequest);
	void update(UpdateCarRequest createCarRequest);
	CarDto getById(int carId);
	void deleteById(int carId);
}
