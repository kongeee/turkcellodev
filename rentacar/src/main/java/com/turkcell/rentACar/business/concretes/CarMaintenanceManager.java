package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.CarDao;
import com.turkcell.rentACar.dataAccess.abstracts.CarMaintenanceDao;
import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService 
{

	private ModelMapperService modelMapperService;
	private CarMaintenanceDao carMaintenanceDao;
	private CarRentalService carRentalService;
	private CarService carService;

	@Autowired
	public CarMaintenanceManager(ModelMapperService modelMapperService,
			CarMaintenanceDao carMaintenanceDao, @Lazy CarRentalService carRentalService,
			CarService carService) 
	{
		this.modelMapperService = modelMapperService;
		this.carMaintenanceDao = carMaintenanceDao;
		this.carRentalService = carRentalService;
		this.carService= carService;
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getAll() 
	{
		List<CarMaintenance> result = this.carMaintenanceDao.findAll();
		List<CarMaintenanceListDto> response = result.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.CAR_MAINTENANCE_LISTED);
	}

	@Override
	public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException 
	{
		checkIfCarExistsById(createCarMaintenanceRequest.getCarId());
		checkIfItIsMaintainableByRented(createCarMaintenanceRequest.getCarId(), createCarMaintenanceRequest.getReturnDate());
		checkIfItIsMaintainableByMaintenance(createCarMaintenanceRequest.getCarId());

		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(createCarMaintenanceRequest,
				CarMaintenance.class);
		carMaintenance.setMaintenanceId(0);
		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_ADDED);
	}

	@Override
	public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException 
	{
		checkIfCarExistsById(updateCarMaintenanceRequest.getCarId());
		checkIfExistByMaintenanceId(updateCarMaintenanceRequest.getCarId());
		checkIfItIsMaintainableByRented(updateCarMaintenanceRequest.getCarId(), updateCarMaintenanceRequest.getReturnDate());
		checkIfItIsMaintainableByMaintenance(updateCarMaintenanceRequest.getCarId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,
				CarMaintenance.class);
		this.carMaintenanceDao.save(carMaintenance);

		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_UPDATED);
	}

	@Override
	public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException 
	{
		checkIfExistByMaintenanceId(deleteCarMaintenanceRequest.getMaintenanceId());
		
		CarMaintenance carMaintenance = this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,
				CarMaintenance.class);
		this.carMaintenanceDao.deleteById(carMaintenance.getMaintenanceId());

		return new SuccessResult(BusinessMessages.CAR_MAINTENANCE_DELETED);
	}

	@Override
	public DataResult<List<CarMaintenanceListDto>> getByCarId(int id) throws BusinessException 
	{
		checkIfCarExistsById(id);
		
		List<CarMaintenance> carMaintenanceList = carMaintenanceDao.getAllByCar_CarId(id);
		List<CarMaintenanceListDto> response = carMaintenanceList.stream().map(
				carMaintenance -> this.modelMapperService.forDto().map(carMaintenance, CarMaintenanceListDto.class))
				.collect(Collectors.toList());

		return new SuccessDataResult<List<CarMaintenanceListDto>>(response, BusinessMessages.CAR_MAINTENANCE_GETTED);
	}

	@Override
	public DataResult<CarMaintenance> getByCarIdAndReturnDate(int carId, Date returnDate) 
	{
		return new SuccessDataResult<CarMaintenance>(
				this.carMaintenanceDao.getByCar_CarIdAndReturnDate(carId, returnDate));
	}

	private void checkIfItIsMaintainableByRented(int carId, LocalDate localDate) throws BusinessException 
	{
		this.carRentalService.IsACarAvailableOnTheSpecifiedDate(carId, localDate);
	}

	@Override
	public Result checkIfItIsMaintainableByMaintenance(int carId) throws BusinessException 
	{
		var result = this.carMaintenanceDao.getByCar_CarIdAndReturnDate(carId, null);
		
		if(result!=null) 
		{
			throw new BusinessException(BusinessMessages.CAR_MAINTENANCE_ALREADY_EXISTS);
		}

		return new SuccessResult();
		
	}

	private void checkIfExistByMaintenanceId(int maintenanceId) throws BusinessException 
	{
		if(!this.carMaintenanceDao.existsById(maintenanceId)) 
		{
			throw new BusinessException(BusinessMessages.CAR_MAINTENANCE_NOT_FOUND);
		}
	}
	
	private void checkIfCarExistsById(int carId) throws BusinessException 
	{
		carService.checkIfExistByCarId(carId);
	}
}