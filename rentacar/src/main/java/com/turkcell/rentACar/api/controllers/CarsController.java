package com.turkcell.rentACar.api.controllers;

import com.turkcell.rentACar.business.abstracts.CarService;
import com.turkcell.rentACar.business.dtos.CarListDto;
import com.turkcell.rentACar.business.requests.creates.CreateCarRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteCarRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateCarRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarsController 
{
    private CarService carService;

    public CarsController(CarService carService) 
    {
        this.carService = carService;
    }

    @GetMapping("/getAll")
    public DataResult<List<CarListDto>> getAll(){
        return this.carService.getAll();
    }
    @GetMapping("/getById")
    public DataResult<CarListDto> getById(@RequestParam int id) throws BusinessException
    {
    	return this.carService.getById(id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateCarRequest createCarRequest) throws BusinessException 
    {
        return this.carService.add(createCarRequest);
    }

    @PutMapping("/update")
    public Result update(@RequestBody UpdateCarRequest updateCarRequest) throws BusinessException
    {
        return this.carService.update(updateCarRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteCarRequest deleteCarRequest) throws BusinessException
    {
        return this.carService.delete(deleteCarRequest);
    }

    @GetMapping("/getByCarDailyPriceLessThanOrEqual")
    public DataResult<List<CarListDto>> getByCarDailyPriceLessThanOrEqual(@RequestParam Double carDailyPrice)
    {
        return this.carService.getByCarDailyPriceLessThanOrEqual(carDailyPrice);
    }

    @GetMapping("/getAllPaged")
    public DataResult<List<CarListDto>> getAllPaged(@RequestParam int pageNo,int pageSize)
    {
        return this.carService.getAllPaged(pageNo,pageSize);
    }

    @GetMapping("/getAllSorted")
    public DataResult<List<CarListDto>> getAllSorted(@RequestParam Sort.Direction direction)
    {
        return this.carService.getAllSorted(direction);
    }

    @PostMapping("/checkIfExistByCarId")
    public Result checkIfExistByCarId(int carId) throws BusinessException
    {
        return this.carService.checkIfExistByCarId(carId);
    }

    @PostMapping("/updateKilometerInformation")
    public Result updateKilometerInformation(int carId,double kilometerInformation) throws BusinessException
    {
        return this.carService.updateKilometerInformation(carId, kilometerInformation);
    }
    
}
