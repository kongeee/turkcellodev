package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.CarDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.CarDao;

import com.turkcell.rentacar.entities.concretes.Car;

@Service
public class CarManager implements CarService {
	
	private CarDao carDao;
	private ModelMapperService modelMapperService;

	public CarManager(CarDao carDao, ModelMapperService modelMapperService) {
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<CarListDto> getAll() {
		List<Car> result = carDao.findAll();
		
		List<CarListDto> response = result.stream()
				.map(car->this.modelMapperService.forDto().map(car, CarListDto.class))
				.collect(Collectors.toList());
		
		return response;
	}

	@Override
	public void add(CreateCarRequest createCarRequest) {
		Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

		this.carDao.save(car);
		
	}

	@Override
	public void update(UpdateCarRequest updateCarRequest) {
		Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);
		
		this.carDao.save(car);
		
		
	}

	@Override
	public void deleteById(int carId) {
		this.carDao.deleteById(carId);
		
	}

	@Override
	public CarDto getById(int carId) {
		Car car = this.carDao.getById(carId);
		
		CarDto response = this.modelMapperService.forDto().map(car, CarDto.class);
		
		return response;
	}
	
}
