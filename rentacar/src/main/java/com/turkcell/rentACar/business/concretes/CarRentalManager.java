package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentACar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.dtos.CarRentalDto;
import com.turkcell.rentACar.business.dtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.creates.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
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
	private CarService carService;
	private AdditionalServiceService additionalServiceService;
	private OrderedAdditionalServiceService orderedAdditionalServiceService;

	@Autowired
	public CarRentalManager(ModelMapperService modelMapperService, 
			CarRentalDao carRentalDao,
			@Lazy CarMaintenanceService carMaintenanceService, 
			CarService carService,
			AdditionalServiceService additionalServiceService,
			@Lazy OrderedAdditionalServiceService orderedAdditionalServiceService) 
	{
		this.modelMapperService = modelMapperService;
		this.carRentalDao = carRentalDao;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;
		this.additionalServiceService = additionalServiceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	}

	@Override
	public DataResult<List<CarRentalDto>> getAll() {
		List<CarRental> result = this.carRentalDao.findAll();
		List<CarRentalDto> response = result.stream()
				.map(carRental -> this.modelMapperService.forDto().map(carRental, CarRentalDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarRentalDto>>(response, "Car Rental Listed Successfully");
	}
	/*
	@Override
	public Result add(CreateCarRentalRequest createCarRentalRequest) throws BusinessException 
	{
		checkIfCarExistsById(createCarRentalRequest.getCarId());
		checkIfCarMaintenance(createCarRentalRequest.getCarId());
		checkIfItCanBeRented(createCarRentalRequest);

		var price = calculatePrice(createCarRentalRequest);

		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest, CarRental.class);
		carRental.setCarRentalId(0);
		carRental.setPrice(price.getData());
		carRental.setRentedDays(findTheNumberOfDaysToRent(carRental.getStartDate(), carRental.getReturnDate()));

		this.carRentalDao.save(carRental);

		carRental= this.carRentalDao.getByRecentlyAddedVehicleId(createCarRentalRequest.getCarId());

		insertAddtionalServices(createCarRentalRequest.getAddtionalServicesId(),carRental.getCarRentalId());

		return new SuccessResult("Car Rental Added Successfully");
	}
	*/
	@Override
	public Result rentForIndividualCustomer(
			CreateCarRentalForIndividualCustomerRequest createCarRentalForIndividualCustomerRequest) throws BusinessException {
		
				checkIfCarExistsById(createCarRentalForIndividualCustomerRequest.getCarId());
				checkIfCarMaintenance(createCarRentalForIndividualCustomerRequest.getCarId());
				checkIfItCanBeRented(createCarRentalForIndividualCustomerRequest.getStartDate(), createCarRentalForIndividualCustomerRequest.getReturnDate(), createCarRentalForIndividualCustomerRequest.getCarId());
		
				var price = calculatePrice(createCarRentalForIndividualCustomerRequest.getCarId(), createCarRentalForIndividualCustomerRequest.getStartDate(), createCarRentalForIndividualCustomerRequest.getReturnDate(),
				createCarRentalForIndividualCustomerRequest.getAdditionalServiceIds(),
				createCarRentalForIndividualCustomerRequest.getStartCity(),
				createCarRentalForIndividualCustomerRequest.getEndCity());
		
				CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalForIndividualCustomerRequest, CarRental.class);
				carRental.setCarRentalId(0);
				carRental.setPrice(price);
				carRental.setRentedDays(findTheNumberOfDaysToRent(carRental.getStartDate(), carRental.getReturnDate()));
		
				this.carRentalDao.save(carRental);
		
				carRental= this.carRentalDao.getByRecentlyAddedVehicleId(createCarRentalForIndividualCustomerRequest.getCarId());
		
				insertAddtionalServices(createCarRentalForIndividualCustomerRequest.getAdditionalServiceIds(),carRental.getCarRentalId());
		
				return new SuccessResult("Car Rental Added Successfully");
	}

	@Override
	public Result rentForCorporateCustomer(
		CreateCarRentalForCorporateCustomerRequest createCarRentalForCorporateCustomerRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Result update(UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException 
	{
		checkIfCarExistsById(updateCarRentalRequest.getCarId());
		checkIfCarRentalExistsById(updateCarRentalRequest.getCarRentalId());
		
		CarRental carRental = this.modelMapperService.forRequest().map(updateCarRentalRequest, CarRental.class);
		this.carRentalDao.save(carRental);
		
		return new SuccessResult("Car Rental Updated Successfully");
	}

	@Override
	public Result delete(DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException 
	{
		checkIfCarRentalExistsById(deleteCarRentalRequest.getCarRentalId());
		
		CarRental carRental = this.modelMapperService.forRequest().map(deleteCarRentalRequest, CarRental.class);
		this.carRentalDao.deleteById(carRental.getCarRentalId());
		
		return new SuccessResult("Car Rental Deleted Successfully");
	}

	@Override
	public DataResult<List<CarRentalListDto>> getByCarId(int id) throws BusinessException 
	{
		checkIfCarExistsById(id);
		
		List<CarRental> result = this.carRentalDao.getAllByCar_CarId(id);
		List<CarRentalListDto> response = result.stream()
				.map(carRental -> this.modelMapperService.forDto().map(carRental, CarRentalListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarRentalListDto>>(response, "Car Rental Listed Successfully");
	}

	@Override
	public DataResult<Boolean> IsAVehicleAvailableOnTheSpecifiedDate(int carId, LocalDate returnDate)
			throws BusinessException 
	{
		checkIfCarExistsById(carId);
		
		var result = this.carRentalDao.getRentalInformationOfTheVehicleOnTheSpecifiedDate(returnDate, returnDate,
				carId);

		if (result.size() == 0) 
		{
			return new SuccessDataResult<Boolean>(true, "Car can be rented");
		}
		throw new BusinessException("The vehicle was rented on the specified date");
	}
	/*
	@Override
	public DataResult<Double> calculatePrice(CreateCarRentalRequest createCarRentalRequest) throws BusinessException 
	{

		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalRequest, CarRental.class);
		CarListDto car = this.carService.getById(carRental.getCar().getCarId()).getData();

		long days = findTheNumberOfDaysToRent(createCarRentalRequest.getStartDate(),
				createCarRentalRequest.getReturnDate());

		var additionalServices = this.additionalServiceService.getAdditionalServicesByIds(createCarRentalRequest.getAddtionalServicesId());

		double price = days * car.getCarDailyPrice() + calculateExtraPriceByCityDistance(carRental);

		price += additionalServiceService.(days,additionalServices.getData());

		return new SuccessDataResult<Double>(price, "Total Price");
	}
	*/

	private double calculatePrice(int carId, LocalDate startDate, LocalDate returnDate, List<Integer> additionalServiceIds, String startCity, String endCity) throws BusinessException{
		
		CarListDto car = this.carService.getById(carId).getData();

		long days = findTheNumberOfDaysToRent(startDate, returnDate);

		double price = days * car.getCarDailyPrice() + calculateExtraPriceByCityDistance(startCity, endCity);

		price += additionalServiceService.calculateAdditionalServicePrice(days, additionalServiceIds).getData();
		return price;
	}



	
	@Override
	public DataResult<CarRentalDto> getById(int id) 
	{
		
		CarRental carRental = this.carRentalDao.getById(id);
		CarRentalDto carRentalDto = this.modelMapperService.forDto().map(carRental,CarRentalDto.class);
		
		return new SuccessDataResult<CarRentalDto>(carRentalDto,"Car Rental Getted Succesfully");
	}
	
	private void insertAddtionalServices(List<Integer> AddtionalServices , int carRentalId) throws BusinessException 
	{
		for(var item : AddtionalServices) 
		{
			CreateOrderedAdditionalServiceRequest createOrderedAdditionalServiceRequest= new CreateOrderedAdditionalServiceRequest();
			createOrderedAdditionalServiceRequest.setAdditionalServiceId(item);
			createOrderedAdditionalServiceRequest.setCarRentalId(carRentalId);
			this.orderedAdditionalServiceService.add(createOrderedAdditionalServiceRequest);
		}
	}

	private boolean checkIfItCanBeRented(LocalDate startDate, LocalDate returnDate, int carId) throws BusinessException 
	{
		var result = this.carRentalDao.getRentalInformationOfTheVehicleOnTheSpecifiedDate(
			startDate, returnDate, carId);
		if (result.size() > 0) 
		{
			throw new BusinessException("Rented between the dates specified in the vehicle");
		}
		return true;
	}

	private long findTheNumberOfDaysToRent(LocalDate startDate, LocalDate returnDate) throws BusinessException 
	{
		long days = ChronoUnit.DAYS.between(startDate, returnDate);
		if (days > 0)
			return days;
		throw new BusinessException("Return date cannot be earlier than start date!");
	}

	private Result checkIfCarMaintenance(int carId) throws BusinessException 
	{
		var result = this.carMaintenanceService.getByCar_CarIdAndReturnDate(carId, null);

		if (result.getData() == null) {

			return new SuccessResult("rented");
		}
		throw new BusinessException("in car maintenance");
	}

	private double calculateExtraPriceByCityDistance(String startCity, String endCity) 
	{
		if (startCity.equals(endCity)) 
		{
			return 0;
		}
		return 750;
	}
	
	private void checkIfCarExistsById(int carId) throws BusinessException 
	{
		this.carService.getById(carId);
	}
	
	private void checkIfCarRentalExistsById(int carRentalId) throws BusinessException 
	{
		this.carRentalDao.getById(carRentalId);
	}

	



}
