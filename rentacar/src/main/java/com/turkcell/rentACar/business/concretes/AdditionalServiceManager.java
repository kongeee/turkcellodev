package com.turkcell.rentACar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import com.turkcell.rentACar.business.abstracts.AdditionalServiceService;
import com.turkcell.rentACar.business.dtos.AdditionalServiceDto;
import com.turkcell.rentACar.business.dtos.AdditionalServiceListDto;
import com.turkcell.rentACar.business.requests.creates.CreateAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteAdditionalServiceRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateAdditionalServiceRequest;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;
import com.turkcell.rentACar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentACar.core.utilities.results.SuccessResult;
import com.turkcell.rentACar.dataAccess.abstracts.AdditionalServiceDao;
import com.turkcell.rentACar.entities.concretes.AdditionalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdditionalServiceManager implements AdditionalServiceService {
    
    private AdditionalServiceDao additionalServiceDao;
    private ModelMapperService modelMapperService;

    @Autowired
    public AdditionalServiceManager(AdditionalServiceDao additionalServiceDao, ModelMapperService modelMapperService) {
        this.additionalServiceDao = additionalServiceDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public Result add(CreateAdditionalServiceRequest createAdditionalServiceRequest) {
        AdditionalService additionalService = this.modelMapperService.forRequest().map(createAdditionalServiceRequest,AdditionalService.class);
        
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult("Additional Service Added Successfully");
    }

    @Override
    public Result update(UpdateAdditionalServiceRequest updateAdditionalServiceRequest) {
        
        AdditionalService additionalService = this.additionalServiceDao.getById(updateAdditionalServiceRequest.getAdditionalServiceId());
        additionalService = this.modelMapperService.forRequest().map(updateAdditionalServiceRequest,AdditionalService.class);
        
        this.additionalServiceDao.save(additionalService);
        return new SuccessResult("Additional Service Updated Successfully");
    }

    @Override
    public Result delete(DeleteAdditionalServiceRequest deleteAdditionalServiceRequest) {
        
        AdditionalService additionalService = this.modelMapperService.forRequest().map(deleteAdditionalServiceRequest,AdditionalService.class);
        this.additionalServiceDao.delete(additionalService);
        return new SuccessResult("Additional Service Deleted Successfully");
    }

    @Override
    public DataResult<List<AdditionalServiceListDto>> getAll() {
        List<AdditionalService> result=this.additionalServiceDao.findAll();
        
        List<AdditionalServiceListDto> response = result.stream().map(additionalService->this.modelMapperService.forDto().map(additionalService,AdditionalServiceListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<AdditionalServiceListDto>>(response,"Additional Services Listed Successfully");
    }

    @Override
    public DataResult<AdditionalServiceDto> getById(int additionalServiceId) {
           
        AdditionalService additionalService = additionalServiceDao.getById(additionalServiceId);
        AdditionalServiceDto additionalServiceDto = this.modelMapperService.forDto().map(additionalService,AdditionalServiceDto.class);
        return new SuccessDataResult<AdditionalServiceDto>(additionalServiceDto,"Additional Service Listed Successfully");
    }

    
}
