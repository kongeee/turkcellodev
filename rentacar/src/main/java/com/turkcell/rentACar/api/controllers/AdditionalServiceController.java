package com.turkcell.rentACar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.dtos.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

@RestController
@RequestMapping("/api/AdditionalServices")
public class AdditionalServiceController 
{

	private AdditionalServiceService additionalServiceService;

    public AdditionalServiceController(AdditionalServiceService additionalServiceService) 
    {
        this.additionalServiceService = additionalServiceService;
    }

    @GetMapping("/getAll")
    public DataResult<List<AdditionalServiceListDto>> getAll()
    {
        return this.additionalServiceService.getAll();
    }

    @GetMapping("/getById")
    public DataResult<AdditionalServiceDto> getById(@RequestParam int id) throws BusinessException
    {
    	return this.additionalServiceService.getById(id);
    }

    @PostMapping("/add")
    public Result add(@RequestBody CreateAdditionalServiceRequest createAdditionalServiceRequest) throws BusinessException 
    {
        return this.additionalServiceService.add(createAdditionalServiceRequest);
    }
    
    @PutMapping("/update")
    public Result update(@RequestBody UpdateAdditionalServiceRequest updateAdditionalServiceRequest) throws BusinessException
    {
        return this.additionalServiceService.update(updateAdditionalServiceRequest);
    }

    @DeleteMapping("/delete")
    public Result delete(@RequestBody DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) throws BusinessException
    {
        return this.additionalServiceService.delete(deleteAdditionalServiceRequest);
    }

    @PostMapping("/calculateAdditionalServicePrice")
    public DataResult<Double> calculateAdditionalServicePrice(long days, List<Integer> additionalServiceIds) throws BusinessException 
    {
        return this.additionalServiceService.calculateAdditionalServicePrice(days, additionalServiceIds);
    }
}
