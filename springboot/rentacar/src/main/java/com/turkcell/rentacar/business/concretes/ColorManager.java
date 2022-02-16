package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.ColorService;

import com.turkcell.rentacar.business.dtos.ColorListDto;

import com.turkcell.rentacar.business.dtos.GetColorDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;

import com.turkcell.rentacar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService{
	
	private ColorDao colorDao;
	private ModelMapperService modelMapperService;
	
	@Autowired
	public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {
		
		this.colorDao = colorDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public List<ColorListDto> getAll() {
		List<Color> result = this.colorDao.findAll();
		
		List<ColorListDto> response = result.stream()
				.map(color -> modelMapperService.forDto().map(color, ColorListDto.class))
				.collect(Collectors.toList());
		return response;
	}

	@Override
	public void add(CreateColorRequest createColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		CheckIfColorNameUnique(color.getName());
		
		this.colorDao.save(color);
		
	}
	
	@Override
	public GetColorDto getById(int id) {
		Color result = colorDao.getById(id);
		
		GetColorDto response = this.modelMapperService.forDto()
							   .map(result, GetColorDto.class);
		
		return response;
	}
	
	private boolean CheckIfColorNameUnique(String colorName) throws BusinessException {
		
		for(ColorListDto color : this.getAll()) {
			if(color.getName().equals(colorName)) {
				throw new BusinessException("Aynı isme sahip birden fazla renk olamaz!");
			}
		}
		return true;
	}
	
}
