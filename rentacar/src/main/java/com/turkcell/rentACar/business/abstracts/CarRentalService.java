package com.turkcell.rentACar.business.abstracts;

import java.util.Date;
import java.util.List;

import com.turkcell.rentACar.business.dtos.CarRentalDto;
import com.turkcell.rentACar.business.dtos.CarRentalListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRentalRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRentalRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRentalRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.CarRental;

public interface CarRentalService {

    DataResult<List<CarRentalDto>> getAll();
    Result add(CreateCarRentalRequest createCarRentalRequest)  throws BusinessException;
    Result update(UpdateCarRentalRequest updateCarRentalRequest);
    Result delete(DeleteCarRentalRequest deleteCarRentalRequest);
    DataResult<List<CarRentalListDto>> getByCarId(int id);
    DataResult<CarRental> getByCar_CarIdAndReturnDate(int carId,Date returnDate);
    DataResult<Double> calculatePrice(CreateCarRentalRequest createCarRentalRequest);
}
