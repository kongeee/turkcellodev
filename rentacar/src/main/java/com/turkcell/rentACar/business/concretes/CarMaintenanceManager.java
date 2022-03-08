package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarMaintenanceService;
import com.turkcell.rentACar.business.abstracts.CarRentalService;
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

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarMaintenanceManager implements CarMaintenanceService {

    private ModelMapperService modelMapperService;
    private CarMaintenanceDao carMaintenanceDao;
    private CarRentalService carRentalService;

    @Autowired
    @Lazy
    public CarMaintenanceManager(ModelMapperService modelMapperService, CarDao carDao, CarMaintenanceDao carMaintenanceDao,CarRentalService carRentalService) {
        this.modelMapperService = modelMapperService;
        this.carMaintenanceDao = carMaintenanceDao;
        this.carRentalService=carRentalService;
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getAll() {
        List<CarMaintenance> result=this.carMaintenanceDao.findAll();
        if(result.isEmpty())
            return new ErrorDataResult<List<CarMaintenanceListDto>>(null,"Current List is Empty");
        List<CarMaintenanceListDto> response = result.stream().map(carMaintenance->this.modelMapperService.forDto().map(carMaintenance,CarMaintenanceListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarMaintenanceListDto>>(response,"Car Maintenances Listed Successfully");
    }

    @Override
    public Result add(CreateCarMaintenanceRequest createCarMaintenanceRequest) throws BusinessException {
        maintenance(createCarMaintenanceRequest.getCarId());
    	
    	CarMaintenance carMaintenance =this.modelMapperService.forRequest().map(createCarMaintenanceRequest,CarMaintenance.class);
        carMaintenance.setMaintenanceId(0);
        this.carMaintenanceDao.save(carMaintenance);
        return new SuccessResult("Car Maintenance Added Successfully");
    }

    @Override
    public Result update(UpdateCarMaintenanceRequest updateCarMaintenanceRequest) {
        CarMaintenance carMaintenance =this.modelMapperService.forRequest().map(updateCarMaintenanceRequest,CarMaintenance.class);
        this.carMaintenanceDao.save(carMaintenance);
        return new SuccessResult("Car Maintenance Updated Successfully");
    }

    @Override
    public Result delete(DeleteCarMaintenanceRequest deleteCarMaintenanceRequest) {
        CarMaintenance carMaintenance =this.modelMapperService.forRequest().map(deleteCarMaintenanceRequest,CarMaintenance.class);
        this.carMaintenanceDao.deleteById(carMaintenance.getMaintenanceId());
        return new SuccessResult("Car Maintenance Deleted Successfully");
    }

    @Override
    public DataResult<List<CarMaintenanceListDto>> getByCarId(int id) {
        List<CarMaintenance> carMaintenanceList = carMaintenanceDao.getAllByCar_CarId(id);
        if(carMaintenanceList.isEmpty())
            return new ErrorDataResult<List<CarMaintenanceListDto>>(null,"Current List is Empty");
        List<CarMaintenanceListDto> response = carMaintenanceList.stream().map(carMaintenance->this.modelMapperService.forDto().map(carMaintenance,CarMaintenanceListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<CarMaintenanceListDto>>(response,"Car Maintenances Listed Successfully");
    }

	@Override
	public DataResult<CarMaintenance> getByCar_CarIdAndReturnDate(int carId,Date returnDate) {
		return new SuccessDataResult<CarMaintenance>(this.carMaintenanceDao.getByCar_CarIdAndReturnDate(carId,returnDate));
	}
	
	private Result maintenance(int carId) throws BusinessException {
	var result=	this.carRentalService.getByCar_CarIdAndReturnDate(carId,null);
		
		if(result.getData()==null) {
			return new SuccessResult("car available");
		}
		throw new BusinessException("car not available");
	}
}
