package com.turkcell.rentACar.business.abstracts;

import java.time.LocalDate;
import java.util.List;

import com.turkcell.rentACar.business.dtos.CarRentalDto;
import com.turkcell.rentACar.business.dtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

public interface CarRentalService 
{
    DataResult<List<CarRentalDto>> getAll();
    Result add(CreateCarRentalRequest createCarRentalRequest)  throws BusinessException;
    Result update(UpdateCarRentalRequest updateCarRentalRequest) throws BusinessException;
    Result delete(DeleteCarRentalRequest deleteCarRentalRequest) throws BusinessException;
    DataResult<List<CarRentalListDto>> getByCarId(int id) throws BusinessException;
    DataResult<CarRentalDto> getById(int id);
    DataResult<Boolean> IsAVehicleAvailableOnTheSpecifiedDate(int carId,LocalDate returnDate) throws BusinessException;
    DataResult<Double> calculatePrice(CreateCarRentalRequest createCarRentalRequest) throws BusinessException ;
}
