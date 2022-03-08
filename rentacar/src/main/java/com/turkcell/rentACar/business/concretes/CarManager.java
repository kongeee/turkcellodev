package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.entities.concretes.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarManager implements CarService {
	private CarDao carDao;
	private CarMaintenanceService carMaintenanceService;
	private ModelMapperService modelMapperService;

	@Autowired
	public CarManager(CarDao carDao, ModelMapperService modelMapperService, CarMaintenanceService carMaintenanceService) {
		this.carDao = carDao;
		this.modelMapperService = modelMapperService;
		this.carMaintenanceService=carMaintenanceService;
	}


	@Override
	public DataResult<List<CarListDto>> getAll() {
		List<Car> result = this.carDao.findAll();
		if(result.isEmpty())
			return new ErrorDataResult<List<CarListDto>>(null,"Current List is Empty");
		List<CarListDto> response = result.stream().map(car->this.modelMapperService.forDto().map(car,CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response,"Cars Listed succesfully");
	}

	@Override
	public Result add(CreateCarRequest createCarRequest) throws BusinessException {
		//checkIfColorAndBrandIsAvailable(createCarRequest.getColorId(),createCarRequest.getBrandId());
		Car car = this.modelMapperService.forRequest().map(createCarRequest,Car.class);
		this.carDao.save(car);
		return new SuccessResult("Car Added Successfully");
	}

	@Override
	public Result update(UpdateCarRequest updateCarRequest) throws BusinessException{
		//checkIfColorAndBrandIsAvailable(updateCarRequest.getColorId(),updateCarRequest.getBrandId());
		if(this.carDao.getAllByCarId(updateCarRequest.getCarId()).stream().count()==0)
			return new ErrorResult("Car Does Not Exist");
		Car car = carDao.getById(updateCarRequest.getCarId());
		car = this.modelMapperService.forRequest().map(updateCarRequest,Car.class);
		this.carDao.save(car);
		return new SuccessResult("Car Updated Successfully");
	}

	@Override
	public Result delete(DeleteCarRequest deleteCarRequest) {
		if(this.carDao.getAllByCarId(deleteCarRequest.getCarId()).stream().count()==0)
			return new ErrorResult("Car Does Not Exist");
		Car car = this.modelMapperService.forRequest().map(deleteCarRequest,Car.class);
		this.carDao.delete(car);
		return new SuccessResult("Car Deleted Succesfully");
	}

	@Override
	public DataResult<CarListDto> getById(int id) {
		if(this.carDao.getAllByCarId(id).stream().count()==0)
			return new ErrorDataResult(null,"Car Does Not Exist");
		Car car = this.carDao.getById(id);
		CarListDto carListDto = this.modelMapperService.forDto().map(car,CarListDto.class);
		return new SuccessDataResult<CarListDto>(carListDto,"Car Getted Succesfully");
	}

	@Override
	public DataResult<List<CarListDto>> getByCarDailyPriceLessThanOrEqual(Double carDailyPrice) {
		Sort sort = Sort.by(Sort.Direction.DESC,"carDailyPrice");
		List<Car> result = this.carDao.getByCarDailyPriceLessThanEqual(carDailyPrice,sort);
		if(result.isEmpty())
			return new ErrorDataResult<List<CarListDto>>(null,"No Car Found Below Daily Price");
		List<CarListDto> response = result.stream().map(car->this.modelMapperService.forDto().map(car,CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response,"Cars Listed succesfully");
	}

	@Override
	public DataResult<List<CarListDto>> getAllPaged(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo,pageSize);
		List<Car> result = this.carDao.findAll();
		if(result.isEmpty())
			return new ErrorDataResult<List<CarListDto>>(null,"Cars Could Not Listed");
		List<CarListDto> response = result.stream().map(car->this.modelMapperService.forDto().map(car,CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response,"Cars Listed succesfully");
	}

	@Override
	public DataResult<List<CarListDto>> getAllSorted(Sort.Direction direction) {
		Sort sort = Sort.by(direction,"carDailyPrice");
		List<Car> result =this.carDao.findAll(sort);
		if(result.isEmpty())
			return new ErrorDataResult<List<CarListDto>>(null,"Cars Could Not Listed");
		List<CarListDto> response = result.stream().map(car->this.modelMapperService.forDto().map(car,CarListDto.class)).collect(Collectors.toList());
		return new SuccessDataResult<List<CarListDto>>(response,"Cars Listed succesfully");
	}
	
}
