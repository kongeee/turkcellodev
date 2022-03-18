package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.CarCrashInformationService;
import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarCrashInformationGetDto;
import com.turkcell.rentACar.business.dtos.CarCrashInformationListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarCrashInformationRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarCrashInformationRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarCrashInformationRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.ErrorDataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.CarCrashInformationDao;
import com.turkcell.rentACar.entities.concretes.CarCrashInformation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CarCrashInformationManager implements CarCrashInformationService 
{
    private CarCrashInformationDao carCrashInformationDao;
    private ModelMapperService modelMapperService;
    private CarService carService;

    @Autowired
    public CarCrashInformationManager(CarCrashInformationDao carCrashInformationDao, ModelMapperService modelMapperService, CarService carService) 
    {
        this.carCrashInformationDao = carCrashInformationDao;
        this.modelMapperService = modelMapperService;
        this.carService = carService;
    }

    @Override
    public Result add(CreateCarCrashInformationRequest createCarCrashInformationRequest) throws BusinessException 
    {
        checkIfCarExists(createCarCrashInformationRequest.getCarId());

        CarCrashInformation carCrashInformation = this.modelMapperService.forRequest().map(createCarCrashInformationRequest, CarCrashInformation.class);
        carCrashInformation.setCarCrashInformationId(0);

        this.carCrashInformationDao.save(carCrashInformation);

        return new SuccessResult("Car Crash Information Added Successfully");
    }

    @Override
    public Result delete(DeleteCarCrashInformationRequest deleteCarCrashInformationRequest) throws BusinessException 
    {
        checkIfCarCrashInformatioExists(deleteCarCrashInformationRequest.getCarCrashInformationId());

        CarCrashInformation carCrashInformation = this.modelMapperService.forRequest().map(deleteCarCrashInformationRequest, CarCrashInformation.class);

        this.carCrashInformationDao.deleteById(carCrashInformation.getCarCrashInformationId());

        return new SuccessResult("Car Crash Information Deleted Successfully");
    }

    @Override
    public DataResult<CarCrashInformationGetDto> getByCarCrashInformationId(int carCrashInformationId) throws BusinessException 
    {
        checkIfCarCrashInformatioExists(carCrashInformationId);

        CarCrashInformation result = this.carCrashInformationDao.getByCarCrashInformationId(carCrashInformationId);

        CarCrashInformationGetDto response = this.modelMapperService.forDto().map(result, CarCrashInformationGetDto.class);

        return new SuccessDataResult<CarCrashInformationGetDto>(response, "Car Crash Information Listed Successfully");
    }

    @Override
    public DataResult<List<CarCrashInformationListDto>> getAll() 
    {
        List<CarCrashInformation> result = this.carCrashInformationDao.findAll();

        List<CarCrashInformationListDto> response = result.stream()
                .map(carCrashInformation -> this.modelMapperService.forDto().map(carCrashInformation, CarCrashInformationListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarCrashInformationListDto>>(response, "Car Crash Information Listed Successfully");
    }

    @Override
    public DataResult<List<CarCrashInformationListDto>> getAllPaged(int pageNo, int pageSize) 
    {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        List<CarCrashInformation> result = this.carCrashInformationDao.findAll(pageable).getContent();

        List<CarCrashInformationListDto> response = result.stream()
                .map(carCrashInformation -> this.modelMapperService.forDto().map(carCrashInformation, CarCrashInformationListDto.class)).collect(Collectors.toList());

        return new SuccessDataResult<List<CarCrashInformationListDto>>(response, "Brands Listed Successfully");
    }

    @Override
    public DataResult<List<CarCrashInformationListDto>> getAllSorted(Sort.Direction direction) 
    {
        Sort s = Sort.by(direction, "carCrashInformationId");

        List<CarCrashInformation> result = this.carCrashInformationDao.findAll(s);

        List<CarCrashInformationListDto> response = result.stream()
                .map(carCrashInformation -> this.modelMapperService.forDto().map(carCrashInformation, CarCrashInformationListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<CarCrashInformationListDto>>(response);
    }

    @Override
    public Result update(UpdateCarCrashInformationRequest updateCarCrashInformationRequest) throws BusinessException 
    {
        checkIfCarCrashInformatioExists(updateCarCrashInformationRequest.getCarCrashInformationId());

        CarCrashInformation carCrashInformationUpdate = this.modelMapperService.forRequest().map(updateCarCrashInformationRequest, CarCrashInformation.class);

        this.carCrashInformationDao.save(carCrashInformationUpdate);

        return new SuccessResult(carCrashInformationUpdate.getCarCrashInformationId() + " updated..");
    }

    private void checkIfCarCrashInformatioExists(int carCrashInformationId) throws BusinessException 
    {
        if(this.carCrashInformationDao.getByCarCrashInformationId(carCrashInformationId) == null)
        {
            throw new BusinessException("There is no car carsh information with this id");
        }
    }
    
    private void checkIfCarExists(int carId) throws BusinessException 
    {
        if(this.carService.getById(carId).getData() == null)
        {
            throw new BusinessException("There is no carr with this id");
        }
    }
}