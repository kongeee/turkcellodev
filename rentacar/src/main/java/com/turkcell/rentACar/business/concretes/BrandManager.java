package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.BrandService;
import com.turkcell.rentACar.business.dtos.BrandDto;
import com.turkcell.rentACar.business.dtos.BrandListDto;
import com.turkcell.rentACar.business.requests.creates.CreateBrandRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteBrandRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateBrandRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentACar.entities.concretes.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BrandManager implements BrandService 
{
	private BrandDao brandDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public BrandManager(BrandDao brandDao,ModelMapperService modelMapperService) 
	{
		this.brandDao = brandDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<BrandListDto>> getAll() 
	{
		List<Brand> result = this.brandDao.findAll();
		List<BrandListDto> response = result.stream().map(brand->this.modelMapperService.forDto().map(brand,BrandListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<BrandListDto>>(response,"Brands Listed Successfully");
	}

	@Override
	public Result add(CreateBrandRequest createBrandRequest) throws BusinessException 
	{
		checkIfExistBrandName(createBrandRequest.getBrandName());

		Brand brand = this.modelMapperService.forRequest().map(createBrandRequest,Brand.class);
		this.brandDao.save(brand);

		return new SuccessResult("Brand Added Successfully");
	}

	@Override
	public DataResult<BrandDto> getById(int id) throws BusinessException 
	{
		checkIfExistByBrandId(id);

		Brand brand = brandDao.getById(id);
		BrandDto brandDto = this.modelMapperService.forDto().map(brand,BrandDto.class);

		return new SuccessDataResult<BrandDto>(brandDto,"Brand Listed Successfully");
	}

	@Override
	public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException 
	{
		checkIfExistByBrandId(updateBrandRequest.getBrandId());
		checkIfExistBrandName(updateBrandRequest.getBrandName());

		Brand brand = this.brandDao.getById(updateBrandRequest.getBrandId());
		brand = this.modelMapperService.forRequest().map(updateBrandRequest,Brand.class);
		this.brandDao.save(brand);

		return new SuccessResult("Brand Updated Successfully");
	}

	@Override
	public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException
	{
		checkIfExistByBrandId(deleteBrandRequest.getBrandId());

		Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest,Brand.class);
		this.brandDao.delete(brand);

		return new SuccessResult("Brand Deleted Successfully");
	}

	private void checkIfExistBrandName(String brandName) throws BusinessException 
	{
		if(this.brandDao.existsByBrandName(brandName)) 
		{
			throw new BusinessException("This brand already exists");
		}	
	}

	private void checkIfExistByBrandId(int brandId) throws BusinessException 
	{
		if(!this.brandDao.existsById(brandId)) 
		{
			throw new BusinessException("Brand not found");
		}
	}
}
