package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CarService;
import com.turkcell.rentacar.business.dtos.CarByIdDto;
import com.turkcell.rentacar.business.dtos.CarListDto;
import com.turkcell.rentacar.business.requests.CreateCarRequest;
import com.turkcell.rentacar.business.requests.UpdateCarRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
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
	    public DataResult<List<CarListDto>> getAll() {
	        List<Car> result = carDao.findAll();

	        List<CarListDto> response = result.stream()
	                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
	                .collect(Collectors.toList());

	        return new SuccessDataResult<List<CarListDto>>(response, "Cars are listed successfuly.");
	    }

	    @Override
	    public Result add(CreateCarRequest createCarRequest) {
	        Car car = this.modelMapperService.forRequest().map(createCarRequest, Car.class);

	        this.carDao.save(car);

	        return new SuccessResult("Car is added.");
	    }

	    @Override
	    public Result update(UpdateCarRequest updateCarRequest) {
	        Car car = this.modelMapperService.forRequest().map(updateCarRequest, Car.class);

	        this.carDao.save(car);

	        return new SuccessResult("Car is updated.");
	    }

	    @Override
	    public Result deleteById(int carId) {
	        this.carDao.deleteById(carId);
	        return new SuccessResult("Car is deleted.");
	    }

	    @Override
	    public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
	        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
	        List<Car> result = carDao.findAll(pageable).getContent();
	        List<CarListDto> response = result.stream()
	                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
	                .collect(Collectors.toList());
	        return new SuccessDataResult<List<CarListDto>>(response);
	    }

	    @Override
	    public DataResult<List<CarListDto>> getAllSorted(String ascOrDesc) {
	        Sort sort;
	        String value = (ascOrDesc.equals("ASC") || ascOrDesc.equals("DESC") ) ? ascOrDesc : "DESC";

	            sort = Sort.by(Sort.Direction.valueOf(value),"dailyPrice");

	        List<Car> result = carDao.findAll(sort);
	        List<CarListDto> response = result.stream()
	                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
	                .collect(Collectors.toList());
	        return new SuccessDataResult<List<CarListDto>>(response);
	    }

	    @Override
	    public DataResult<List<CarListDto>> getByDailyPriceIsLessThanEqual(double dailyPrice) {
	        List<Car> result = carDao.getByDailyPriceIsLessThanEqual(dailyPrice);
	        List<CarListDto> response = result.stream()
	                .map(car -> this.modelMapperService.forDto().map(car, CarListDto.class))
	                .collect(Collectors.toList());

	        return new SuccessDataResult<List<CarListDto>>(response, "Cars are listed successfuly.");
	    }

	    @Override
	    public DataResult<CarByIdDto> getById(int carId) {
	        Car car = this.carDao.getById(carId);

	        CarByIdDto response = this.modelMapperService.forDto().map(car, CarByIdDto.class);

	        return new SuccessDataResult<CarByIdDto>(response);
	    }
	
}
