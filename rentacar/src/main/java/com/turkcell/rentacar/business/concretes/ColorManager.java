package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.turkcell.rentacar.business.abstracts.ColorService;

import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.dtos.ColorDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;

import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;

import com.turkcell.rentacar.entities.concretes.Color;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ColorManager implements ColorService {

	private ColorDao colorDao;
	private ModelMapperService modelMapperService;
	
	@Override
	public List<ColorListDto> getAll() {
	
		List<Color> result = this.colorDao.findAll();
		List<ColorListDto> response = result.stream()
				.map(color->this.modelMapperService.forDto().map(color, ColorListDto.class))
				.collect(Collectors.toList());
				
		return response;
		
	}

	@Override
	public void add(CreateColorRequest createColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);
		
		checkIfColorNameIsUnique(color.getColorName());
		
		
		this.colorDao.save(color);
		
	
		
	}

	@Override
	public ColorDto getById(int colorId) {
		Color color = this.colorDao.getById(colorId);
		
		ColorDto response = this.modelMapperService.forDto().map(color, ColorDto.class);
		
		return response;
	}
	
	
	@Override
	public void update(UpdateColorRequest updateColorRequest) throws BusinessException {
		Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		
		checkIfColorNameIsUnique(color.getColorName());
		
		this.colorDao.save(color);
	}

	@Override
	public void deleteById(int colorId) {
		this.colorDao.deleteById(colorId);
		
	}
	
	private boolean checkIfColorNameIsUnique(String colorName) throws BusinessException {
		
		for(ColorListDto colorElement:this.getAll()) {
			if(colorElement.getColorName().equals(colorName)) {
				throw new BusinessException("Aynı isimde birden fazla renk olamaz");
			}
		}
		
		return true;
		
	}

}
