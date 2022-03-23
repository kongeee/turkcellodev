package com.turkcell.rentACar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentACar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentACar.business.constants.messages.BusinessMessages;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.dtos.CarRentalDto;
import com.turkcell.rentACar.business.dtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarRentalDao;
import com.turkcell.rentACar.entities.concretes.CarRental;

@Service
public class CarRentalManager implements CarRentalService 
{
	private ModelMapperService modelMapperService;
	private CarRentalDao carRentalDao;
	private CarMaintenanceService carMaintenanceService;
	private CarService carService;
	private IndividualCustomerService individualCustomerService;
	private CorporateCustomerService corporateCustomerService;

	@Autowired
	public CarRentalManager(ModelMapperService modelMapperService, 
			CarRentalDao carRentalDao,
			@Lazy CarMaintenanceService carMaintenanceService, 
			CarService carService,
			IndividualCustomerService individualCustomerService, 
			CorporateCustomerService corporateCustomerService) 
	{
		this.modelMapperService = modelMapperService;
		this.carRentalDao = carRentalDao;
		this.carMaintenanceService = carMaintenanceService;
		this.carService = carService;

		this.individualCustomerService = individualCustomerService;
		this.corporateCustomerService = corporateCustomerService;
	}

	@Override
	public DataResult<List<CarRentalListDto>> getAll() 
	{
		List<CarRental> result = this.carRentalDao.findAll();

		List<CarRentalListDto> response = result.stream()
				.map(carRental -> this.modelMapperService.forDto().map(carRental, CarRentalListDto.class))
				.collect(Collectors.toList());
				
		return new SuccessDataResult<List<CarRentalListDto>>(response, BusinessMessages.CAR_RENTAL_LISTED);
	}

	@Override
	public Result rentForIndividualCustomer(
			CreateCarRentalForIndividualCustomerRequest createCarRentalForIndividualCustomerRequest) throws BusinessException 
	{
		checkIfCarExistsById(createCarRentalForIndividualCustomerRequest.getCarId());
		checkIfCarMaintenance(createCarRentalForIndividualCustomerRequest.getCarId());
		checkIfItCanBeRented(createCarRentalForIndividualCustomerRequest.getStartDate(), createCarRentalForIndividualCustomerRequest.getReturnDate(), createCarRentalForIndividualCustomerRequest.getCarId());
		checkIfReturnDateAfterStartDate(createCarRentalForIndividualCustomerRequest.getStartDate(), createCarRentalForIndividualCustomerRequest.getReturnDate());
		checkIfStartDateBeforeToday(createCarRentalForIndividualCustomerRequest.getStartDate());
		checkIfIndividualCustomerExists(createCarRentalForIndividualCustomerRequest.getIndividualCustomerId());

		double kilometerInformation = this.carService.getById(createCarRentalForIndividualCustomerRequest.getCarId()).getData().getKilometerInformation();

		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalForIndividualCustomerRequest, CarRental.class);
		carRental.setCarRentalId(0);
		carRental.setStartingKilometer(kilometerInformation);
		carRental.setReturnKilometer(kilometerInformation);
		carRental.setRentedDays(findTheNumberOfDaysToRent(carRental.getStartDate(), carRental.getReturnDate()));
		carRental.getCustomer().setUserId(createCarRentalForIndividualCustomerRequest.getIndividualCustomerId());

		this.carRentalDao.save(carRental);

		return new SuccessResult(BusinessMessages.CAR_RENTAL_ADDED);
	}

	@Override
	public Result rentForCorporateCustomer(
		CreateCarRentalForCorporateCustomerRequest createCarRentalForCorporateCustomerRequest) throws BusinessException 
	{
		checkIfCarExistsById(createCarRentalForCorporateCustomerRequest.getCarId());
		checkIfCarMaintenance(createCarRentalForCorporateCustomerRequest.getCarId());
		checkIfItCanBeRented(createCarRentalForCorporateCustomerRequest.getStartDate(), createCarRentalForCorporateCustomerRequest.getReturnDate(), createCarRentalForCorporateCustomerRequest.getCarId());
		checkIfReturnDateAfterStartDate(createCarRentalForCorporateCustomerRequest.getStartDate(), createCarRentalForCorporateCustomerRequest.getReturnDate());
		checkIfStartDateBeforeToday(createCarRentalForCorporateCustomerRequest.getStartDate());
		checkIfCorporateCustomerExists(createCarRentalForCorporateCustomerRequest.getCorporateCustomerId());

		double kilometerInformation = this.carService.getById(createCarRentalForCorporateCustomerRequest.getCarId()).getData().getKilometerInformation();

		CarRental carRental = this.modelMapperService.forRequest().map(createCarRentalForCorporateCustomerRequest, CarRental.class);
		carRental.setCarRentalId(0);
		carRental.setStartingKilometer(kilometerInformation);
		carRental.setReturnKilometer(kilometerInformation);
		carRental.setRentedDays(findTheNumberOfDaysToRent(carRental.getStartDate(), carRental.getReturnDate()));
		carRental.getCustomer().setUserId(createCarRentalForCorporateCustomerRequest.getCorporateCustomerId());

		this.carRentalDao.save(carRental);

		return new SuccessResult(BusinessMessages.CAR_RENTAL_ADDED);
	}

	@Override
	public Result updateForCorporateCustomer(UpdateCarRentalForCorporateCustomerRequest updateCarRentalForCorporateCustomerRequest) throws BusinessException 
	{
		checkIfCarExistsById(updateCarRentalForCorporateCustomerRequest.getCarId());
		checkIfCarRentalExistsById(updateCarRentalForCorporateCustomerRequest.getCarRentalId());
		checkIfItCanBeRented(updateCarRentalForCorporateCustomerRequest.getStartDate(), updateCarRentalForCorporateCustomerRequest.getReturnDate(), updateCarRentalForCorporateCustomerRequest.getCarId(),updateCarRentalForCorporateCustomerRequest.getCarRentalId());
		checkIfReturnDateAfterStartDate(updateCarRentalForCorporateCustomerRequest.getStartDate(), updateCarRentalForCorporateCustomerRequest.getReturnDate());
		checkIfStartDateBeforeToday(updateCarRentalForCorporateCustomerRequest.getStartDate());
		checkIfCorporateCustomerExists(updateCarRentalForCorporateCustomerRequest.getCorporateCustomerId());

		double startingKilometer = this.carRentalDao.getById(updateCarRentalForCorporateCustomerRequest.getCarRentalId()).getStartingKilometer(); 

		CarRental carRental = this.modelMapperService.forRequest().map(updateCarRentalForCorporateCustomerRequest, CarRental.class);
		carRental.setStartingKilometer(startingKilometer);
		carRental.setRentedDays(findTheNumberOfDaysToRent(carRental.getStartDate(), carRental.getReturnDate()));
		carRental.getCustomer().setUserId(updateCarRentalForCorporateCustomerRequest.getCorporateCustomerId());

		carRental =	this.carRentalDao.save(carRental);
		
		return new SuccessResult(BusinessMessages.CAR_RENTAL_UPDATED);
	}

	@Override
	public Result updateForIndividualCustomer(UpdateCarRentalForIndividualCustomerRequest updateCarRentalForIndividualCustomerRequest) throws BusinessException 
	{
		checkIfCarExistsById(updateCarRentalForIndividualCustomerRequest.getCarId());
		checkIfCarRentalExistsById(updateCarRentalForIndividualCustomerRequest.getCarRentalId());
		checkIfItCanBeRented(updateCarRentalForIndividualCustomerRequest.getStartDate(), updateCarRentalForIndividualCustomerRequest.getReturnDate(), updateCarRentalForIndividualCustomerRequest.getCarId(),updateCarRentalForIndividualCustomerRequest.getCarRentalId());
		checkIfReturnDateAfterStartDate(updateCarRentalForIndividualCustomerRequest.getStartDate(), updateCarRentalForIndividualCustomerRequest.getReturnDate());
		checkIfStartDateBeforeToday(updateCarRentalForIndividualCustomerRequest.getStartDate());
		checkIfIndividualCustomerExists(updateCarRentalForIndividualCustomerRequest.getIndividualCustomerId());

		double startingKilometer = this.carRentalDao.getById(updateCarRentalForIndividualCustomerRequest.getCarRentalId()).getStartingKilometer(); 

		CarRental carRental = this.modelMapperService.forRequest().map(updateCarRentalForIndividualCustomerRequest, CarRental.class);
		carRental.setStartingKilometer(startingKilometer);
		carRental.setRentedDays(findTheNumberOfDaysToRent(carRental.getStartDate(), carRental.getReturnDate()));
		carRental.getCustomer().setUserId(updateCarRentalForIndividualCustomerRequest.getIndividualCustomerId());

		this.carRentalDao.save(carRental);

		return new SuccessResult(BusinessMessages.CAR_RENTAL_UPDATED);
	}

	@Override
	public Result deleteForIndividualCustomer(DeleteCarRentalForIndividualCustomerRequest deleteCarRentalForIndividualCustomerRequest) throws BusinessException 
	{
		checkIfCarRentalExistsById(deleteCarRentalForIndividualCustomerRequest.getCarRentalId());
		
		this.carRentalDao.deleteById(deleteCarRentalForIndividualCustomerRequest.getCarRentalId());
		
		return new SuccessResult(BusinessMessages.CAR_RENTAL_DELETED);
	}

	@Override
	public Result deleteForCorporateCustomer(DeleteCarRentalForCorporateCustomerRequest deleteCarRentalForCorporateCustomerRequest) throws BusinessException 
	{
		checkIfCarRentalExistsById(deleteCarRentalForCorporateCustomerRequest.getCarRentalId());
		
		CarRental carRental = this.modelMapperService.forRequest().map(deleteCarRentalForCorporateCustomerRequest, CarRental.class);
		this.carRentalDao.deleteById(carRental.getCarRentalId());
		
		return new SuccessResult(BusinessMessages.CAR_RENTAL_DELETED);
	}

	@Override
	public DataResult<List<CarRentalListDto>> getByCarId(int id) throws BusinessException 
	{
		checkIfCarExistsById(id);
		
		List<CarRental> result = this.carRentalDao.getAllByCar_CarId(id);
		List<CarRentalListDto> response = result.stream()
				.map(carRental -> this.modelMapperService.forDto().map(carRental, CarRentalListDto.class))
				.collect(Collectors.toList());
		return new SuccessDataResult<List<CarRentalListDto>>(response, BusinessMessages.CAR_RENTAL_LISTED);
	}

	@Override
	public Result IsACarAvailableOnTheSpecifiedDate(int carId, LocalDate returnDate) throws BusinessException 
	{
		checkIfCarExistsById(carId);
		
		var result = this.carRentalDao.getRentalInformationOfTheCarOnTheSpecifiedDate(returnDate, returnDate,carId);

		if (result.size() > 0) 
		{
			throw new BusinessException(BusinessMessages.CAR_RENTAL_ALREADY_EXISTS_ON_SPECIFIC_DATE);
		}
		return new SuccessResult();
	}

	@Override
	public DataResult<Double> calculatePriceForCorporateCustomer(CreateCarRentalForCorporateCustomerRequest createCarRentalForCorporateCustomerRequest) throws BusinessException
	{
		var result = calculatePrice(createCarRentalForCorporateCustomerRequest.getCarId(), createCarRentalForCorporateCustomerRequest.getStartDate(), createCarRentalForCorporateCustomerRequest.getReturnDate(),
		createCarRentalForCorporateCustomerRequest.getStartCityId(),
		createCarRentalForCorporateCustomerRequest.getEndCityId());

		return new SuccessDataResult<Double>(result);
	}

	@Override
	public DataResult<Double> calculatePriceForIndividaulCustomer(CreateCarRentalForIndividualCustomerRequest createCarRentalForIndividualCustomerRequest) throws BusinessException
	{
		var result = calculatePrice(createCarRentalForIndividualCustomerRequest.getCarId(), createCarRentalForIndividualCustomerRequest.getStartDate(), createCarRentalForIndividualCustomerRequest.getReturnDate(),
		createCarRentalForIndividualCustomerRequest.getStartCityId(),
		createCarRentalForIndividualCustomerRequest.getEndCityId());

		return new SuccessDataResult<Double>(result);
	}

	@Override
	public DataResult<CarRentalDto> getById(int id) 
	{
		CarRental carRental = this.carRentalDao.getById(id);
		CarRentalDto carRentalDto = this.modelMapperService.forDto().map(carRental,CarRentalDto.class);
		
		return new SuccessDataResult<CarRentalDto>(carRentalDto,BusinessMessages.CAR_RENTAL_GETTED);
	}

	@Override
	public Result checkIfCarRentalExistsById(int carRentalId) throws BusinessException 
	{
		var result=	this.carRentalDao.existsByCarRentalId(carRentalId);
		if(result == false)
		{
			throw new BusinessException(BusinessMessages.CAR_RENTAL_NOT_FOUND);
		}

		return new SuccessResult();
	}

	private double calculatePrice(int carId, LocalDate startDate, LocalDate returnDate, int startCityId, int endCityId) throws BusinessException{
		
		CarListDto car = this.carService.getById(carId).getData();

		long days = findTheNumberOfDaysToRent(startDate, returnDate);

		double price = days * car.getCarDailyPrice() + calculateExtraPriceByCityDistance(startCityId, endCityId);
		return price;
	}

	private boolean checkIfItCanBeRented(LocalDate startDate, LocalDate returnDate, int carId) throws BusinessException 
	{
		var result = this.carRentalDao.getRentalInformationOfTheCarOnTheSpecifiedDate(
			startDate, returnDate, carId);
		if (result.size() > 0) 
		{
			throw new BusinessException(BusinessMessages.CAR_RENTAL_ALREADY_EXISTS_ON_SPECIFIC_DATE);
		}
		return true;
	}

	private boolean checkIfItCanBeRented(LocalDate startDate, LocalDate returnDate, int carId,int carRentalId) throws BusinessException 
	{
		var result = this.carRentalDao.getRentalInformationOfTheCarOnTheSpecifiedDate(
			startDate, returnDate, carId, carRentalId);
		if (result.size() > 0) 
		{
			throw new BusinessException(BusinessMessages.CAR_RENTAL_ALREADY_EXISTS_ON_SPECIFIC_DATE);
		}
		return true;
	}

	private long findTheNumberOfDaysToRent(LocalDate startDate, LocalDate returnDate)
	{
		long days = ChronoUnit.DAYS.between(startDate, returnDate);
		
		return (days==0) ? 1 : days;
		
	}

	private void checkIfCarMaintenance(int carId) throws BusinessException 
	{
		this.carMaintenanceService.checkIfItIsMaintainableByMaintenance(carId);
	}

	private double calculateExtraPriceByCityDistance(int startCityId, int endCityId) 
	{
		if (startCityId==endCityId) 
		{
			return 0;
		}
		return 750;
	}
	
	private void checkIfCarExistsById(int carId) throws BusinessException 
	{
		this.carService.checkIfExistByCarId(carId);
	}

	private void checkIfStartDateBeforeToday(LocalDate startDate) throws BusinessException{
		
		LocalDate now = LocalDate.now();

		if(startDate.isBefore(now))
		{
			throw new BusinessException(BusinessMessages.START_DATE_MUST_NOT_BE_BEFORE_TODAY);
		}
	}

	private void checkIfReturnDateAfterStartDate(LocalDate startDate, LocalDate returnDate) throws BusinessException{
		
		if(startDate.isAfter(returnDate))
		{
			throw new BusinessException(BusinessMessages.START_DATE_MUST_NOT_BE_BEFORE_RETURN_DATE);
		}
	}

	private void checkIfCorporateCustomerExists(int corporateCustomerId) throws BusinessException{
		this.corporateCustomerService.checkIfExistByCorporateCustomerId(corporateCustomerId);
	}

	private void checkIfIndividualCustomerExists(int individualCustomerId) throws BusinessException{
		this.individualCustomerService.checkIfExistByIndividualCustomerId(individualCustomerId);
	}
}