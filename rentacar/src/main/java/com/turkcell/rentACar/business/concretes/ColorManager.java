package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.ColorService;
import com.turkcell.rentACar.business.dtos.ColorDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.requests.creates.CreateColorRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteColorRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentACar.core.utilities.results.*;
import com.turkcell.rentACar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentACar.entities.concretes.Color;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;

	@Autowired
	public ColorManager(ColorDao colorDao,ModelMapperService modelMapperService) 
	{
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<ColorListDto>> getAll() 
	{
		List<Color> result = this.colorDao.findAll();
		List<ColorListDto> response = result.stream().map(color->this.modelMapperService.forDto().map(color,ColorListDto.class)).collect(Collectors.toList());

		return new SuccessDataResult<List<ColorListDto>>(response,"Colors Listed Successfully");
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException 
	{	
		checkIfExistColorName(createColorRequest.getColorName());
		
		Color color = this.modelMapperService.forRequest().map(createColorRequest,Color.class);
		this.colorDao.save(color);

		return new SuccessResult("Color Added Successfully");
	}

	@Override
	public DataResult<ColorDto> getById(int id) throws BusinessException 
	{
		checkIfExistByColorId(id);
		
		Color color = colorDao.getById(id);
		ColorDto colorDto = this.modelMapperService.forDto().map(color,ColorDto.class);
		
		return new SuccessDataResult<ColorDto>(colorDto,"Color Listed Successfully");
	}

	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException
	{
		checkIfExistByColorId(updateColorRequest.getColorId());
		checkIfExistColorName(updateColorRequest.getColorName());

		Color color = colorDao.getById(updateColorRequest.getColorId());
		color = this.modelMapperService.forRequest().map(updateColorRequest,Color.class);
		this.colorDao.save(color);
		
		return new SuccessResult("Color Updated Succesfully");
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException
	{
		checkIfExistByColorId(deleteColorRequest.getColorId());

		Color color = this.modelMapperService.forRequest().map(deleteColorRequest,Color.class);
		this.colorDao.delete(color);
		
		return new SuccessResult("Color Deleted Succesfully");
	}

	private void checkIfExistColorName(String colorName) throws BusinessException 
	{
		if(this.colorDao.existsByColorName(colorName)) 
		{
			throw new BusinessException("This color already exists");
		}	
	}

	private void checkIfExistByColorId(int colorId) throws BusinessException 
	{
		if(!this.colorDao.existsById(colorId)) 
		{
			throw new BusinessException("Color not found");
		}
	}
}
