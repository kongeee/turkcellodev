package com.turkcell.rentACar.business.concretes;

import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.dtos.CarRentalDto;
import com.turkcell.rentACar.business.dtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACar.entities.concretes.CarRental;

@Service
public class CarRentalManager implements CarRentalService {

	private ModelMapperService modelMapperService;
	private CarRentalDao carRentalDao;
	private CarMaintenanceService carMaintenanceService;
	
    @Autowired
    @Lazy
	public CarRentalManager(ModelMapperService modelMapperService,CarRentalDao carRentalDao,CarMaintenanceService carMaintenanceService) {
		this.modelMapperService=modelMapperService;
		this.carRentalDao= carRentalDao;
		this.carMaintenanceService=carMaintenanceService;
	}

	@Override
	public DataResult<List<CarRentalDto>> getAll() {
        List<CarRental> result=this.carRentalDao.findAll();
        if(result.isEmpty())
            return new ErrorDataResult<List<CarRentalDto>>(null,"Current List is Empty");
        List<CarRentalDto> response = result.stream().map(carRental->this.modelMapperService.forDto().map(carRental,CarRentalDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarRentalDto>>(response,"Car Rental Listed Successfully");
	}
	@Override
	public Result add(CreateCarRentalRequest createCarRentalRequest)  throws BusinessException {
		rent(createCarRentalRequest.getCarId());
		
		CarRental carRental =this.modelMapperService.forRequest().map(createCarRentalRequest,CarRental.class);
		carRental.setCarRentalId(0);
        this.carRentalDao.save(carRental);
        return new SuccessResult("Car Rental Added Successfully");
	}
	@Override
	public Result update(UpdateCarRentalRequest updateCarRentalRequest) {
		CarRental carRental =this.modelMapperService.forRequest().map(updateCarRentalRequest,CarRental.class);
        this.carRentalDao.save(carRental);
        return new SuccessResult("Car Rental Updated Successfully");
	}
	@Override
	public Result delete(DeleteCarRentalRequest deleteCarRentalRequest) {
		CarRental carRental =this.modelMapperService.forRequest().map(deleteCarRentalRequest,CarRental.class);
        this.carRentalDao.deleteById(carRental.getCarRentalId());
        return new SuccessResult("Car Rental Deleted Successfully");
	}
	@Override
	public DataResult<List<CarRentalListDto>> getByCarId(int id) {
        List<CarRental> result=this.carRentalDao.getAllByCar_CarId(id);
        if(result.isEmpty())
            return new ErrorDataResult<List<CarRentalListDto>>(null,"Current List is Empty");
        List<CarRentalListDto> response = result.stream().map(carRental->this.modelMapperService.forDto().map(carRental,CarRentalListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarRentalListDto>>(response,"Car Rental Listed Successfully");
	}
	@Override
	public DataResult<CarRental> getByCar_CarIdAndReturnDate(int carId, Date returnDate) {
		return new SuccessDataResult<CarRental>(this.carRentalDao.getByCar_CarIdAndReturnDate(carId,returnDate));
	}

	@Override
	public DataResult<Double> calculatePrice(CreateCarRentalRequest createCarRentalRequest) {

		CarRental carRental =this.modelMapperService.forRequest().map(createCarRentalRequest,CarRental.class);
		
		long days = ChronoUnit.DAYS.between(createCarRentalRequest.getStartDate(), carRental.getReturnDate());
		double result = days * carRental.getCar().getCarDailyPrice() + calculateExtraPriceByCityDistance(carRental);
		return new SuccessDataResult<Double>(result, "Total Price");
		
	}
	
	private Result rent(int carId) throws BusinessException {
	var result=	this.carMaintenanceService.getByCar_CarIdAndReturnDate(carId,null);
		
		if(result.getData()==null) {
			return new SuccessResult("rented");
		}
		throw new BusinessException("in car maintenance");
	}

	private double calculateExtraPriceByCityDistance(CarRental carRental){
		

		if(carRental.getStartCityName().equals(carRental.getEndCityName())){
			return 0;
		}
		return 750;
	}

	
}
