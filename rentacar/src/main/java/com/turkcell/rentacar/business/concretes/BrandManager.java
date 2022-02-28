package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandByIdDto;
import com.turkcell.rentacar.business.dtos.BrandListDto;
import com.turkcell.rentacar.business.requests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;
import com.turkcell.rentacar.entities.concretes.Car;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
	private BrandDao brandDao;
    private ModelMapperService modelMapperService;


    @Override
    public DataResult<List<BrandListDto>> getAll() {
        List<Brand> result = this.brandDao.findAll();
        List<BrandListDto> response = result.stream()
                .map(brand -> this.modelMapperService.forDto().map(brand, BrandListDto.class))
                .collect(Collectors.toList());

        return new SuccessDataResult<List<BrandListDto>>(response, "Brand are listed successfully.");
    }

    @Override
    public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {


        Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);

        checkIfBrandNameIsUnique(brand.getBrandName());


        this.brandDao.save(brand);

        return new SuccessResult("Brand is added.");

    }


    @Override
    public DataResult<BrandByIdDto> getById(int brandId) {
        Brand brand = this.brandDao.getById(brandId);

        BrandByIdDto response = this.modelMapperService.forDto().map(brand, BrandByIdDto.class);

        return new SuccessDataResult<BrandByIdDto>(response, "Brand is finded by id.");
    }


    @Override
    public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {
        Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);

        checkIfBrandNameIsUnique(brand.getBrandName());

        this.brandDao.save(brand);
        return new SuccessResult("Brand is updated successfuly.");
    }

    @Override
    public Result deleteById(int brandId) {
        this.brandDao.deleteById(brandId);
        return new SuccessResult("Brand is deleted successfully.");

    }

    private boolean checkIfBrandNameIsUnique(String brandName) throws BusinessException {

        for (BrandListDto brandElement : this.getAll().getData()) {
            if (brandElement.getBrandName().equals(brandName)) {
                throw new BusinessException("Aynı isimde birden fazla marka olamaz");
            }
        }

        return true;

    }
	

}
