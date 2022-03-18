package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CarCrashInformationService;
import com.turkcell.rentACar.business.dtos.CarCrashInformationGetDto;
import com.turkcell.rentACar.business.dtos.CarCrashInformationListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarCrashInformationRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarCrashInformationRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarCrashInformationRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/carCrashInformation")
public class CarCrashInformationController 
{
    private CarCrashInformationService carCrashInformationService;

    @Autowired
    public CarCrashInformationController(CarCrashInformationService carCrashInformationService) 
    {
        this.carCrashInformationService = carCrashInformationService;
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateCarCrashInformationRequest carCrashInformationRequest) throws BusinessException 
    {
        return this.carCrashInformationService.add(carCrashInformationRequest);
    }

    @GetMapping("/getAll")
    public DataResult<List<CarCrashInformationListDto>> getAll() 
    {
        return this.carCrashInformationService.getAll();
    }

    @GetMapping("/getAllSorted")
    public DataResult<List<CarCrashInformationListDto>> getAllSorted(@RequestParam("direction") Sort.Direction direction) 
    {
        return this.carCrashInformationService.getAllSorted(direction);
    }

    @GetMapping("/getAllPaged")
    public DataResult<List<CarCrashInformationListDto>> getAllPaged(@RequestParam("pageNo") int pageNo,
                                                        @RequestParam("pageSize") int pageSize) {
        return this.carCrashInformationService.getAllPaged(pageNo, pageSize);
    }

    @GetMapping("/getByCarCrashInformationId")
    public DataResult<CarCrashInformationGetDto> getByCarCrashInformationId(@RequestParam("carCrashInformationId") int carCrashInformationId) throws BusinessException 
    {
        return this.carCrashInformationService.getByCarCrashInformationId(carCrashInformationId);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateCarCrashInformationRequest updateCarCrashInformationRequest) throws BusinessException 
    {
        return this.carCrashInformationService.update(updateCarCrashInformationRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteCarCrashInformationRequest deleteCarCrashInformationRequest) throws BusinessException 
    {
        return this.carCrashInformationService.delete(deleteCarCrashInformationRequest);
    }
}