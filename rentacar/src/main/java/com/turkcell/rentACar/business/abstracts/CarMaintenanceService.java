package com.turkcell.rentACar.business.abstracts;



import com.turkcell.rentACar.business.dtos.CarMaintenanceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarMaintenanceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarMaintenanceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.entities.concretes.CarMaintenance;

import java.util.Date;
import java.util.List;

public interface CarMaintenanceService {
    DataResult<List<CarMaintenanceListDto>> getAll();
    Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest)  throws BusinessException;
    Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) throws BusinessException;
    Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) throws BusinessException;
    DataResult<List<CarMaintenanceListDto>> getByCarId(int id) throws BusinessException;
    DataResult<CarMaintenance> getByCarIdAndReturnDate(int carId,Date returnDate);
}
