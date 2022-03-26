package com.turkcell.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.api.models.Calculates.CarRental.CalculateCarRentalForCorporateCustomerModel;
import com.turkcell.rentACar.api.models.Calculates.CarRental.CalculateCarRentalForIndividualCustomerModel;
import com.turkcell.rentACar.business.dtos.CarRentalDto;
import com.turkcell.rentACar.business.dtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalForCorporateCustomerRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalForIndividualCustomerRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarRentalService 
{
    DataResult<List<CarRentalListDto>> getAll();
    DataResult<List<CarRentalListDto>> getByCarId(int id) throws BusinessException;
    DataResult<CarRentalDto> getById(int id);

    DataResult<CarRentalDto> rentForIndividualCustomer(CreateCarRentalForIndividualCustomerRequest createCarRentalForIndividualCustomerRequest) throws BusinessException;
    DataResult<CarRentalDto> rentForCorporateCustomer(CreateCarRentalForCorporateCustomerRequest createCarRentalForCorporateCustomerRequest) throws BusinessException;

    Result updateForCorporateCustomer(UpdateCarRentalForCorporateCustomerRequest updateCarRentalForCorporateCustomerRequest) throws BusinessException;
    Result updateForIndividualCustomer(UpdateCarRentalForIndividualCustomerRequest updateCarRentalForIndividualCustomerRequest) throws BusinessException;

    Result updateReturnKilometerForCorporateCustomer(int carRentalId, int corporateCustormerId,double returnKilometer) throws BusinessException;
    Result updateReturnKilometerForIndividualCustomer(int carRentalId, int individualCustomerId,double returnKilometer) throws BusinessException;

    Result updateReturnKilometerAndReturnDateForCorporateCustomer(int carRentalId, int corporateCustormerId,double returnKilometer, LocalDate returnDate) throws BusinessException;
    Result updateReturnKilometerAndReturnDateForIndividualCustomer(int carRentalId, int individualCustomerId,double returnKilometer, LocalDate returnDate) throws BusinessException;

    Result deleteForIndividualCustomer(DeleteCarRentalForIndividualCustomerRequest deleteCarRentalForIndividualCustomerRequest) throws BusinessException;
    Result deleteForCorporateCustomer(DeleteCarRentalForCorporateCustomerRequest deleteCarRentalForCorporateCustomerRequest) throws BusinessException;
    
    Result IsACarAvailableOnTheSpecifiedDate(int carId,LocalDate returnDate) throws BusinessException;
    DataResult<Double> calculatePriceForIndividualCustomer(CalculateCarRentalForIndividualCustomerModel calculateCarRentalForIndividualCustomerModel) throws BusinessException;
    DataResult<Double> calculatePriceForCorporateCustomer(CalculateCarRentalForCorporateCustomerModel calculateCarRentalForCorporateCustomerModel) throws BusinessException;

    Result checkIfCarRentalExistsById(int carRentalId) throws BusinessException;
}
