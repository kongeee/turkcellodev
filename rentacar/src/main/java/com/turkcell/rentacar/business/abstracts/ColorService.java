package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.ColorDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.entities.concretes.Color;

public interface ColorService {

	List<ColorListDto> getAll();
	void add(CreateColorRequest createColorRequest) throws BusinessException;
	ColorDto getById(int id);
	
	void update(UpdateColorRequest updateColorRequest) throws BusinessException;
	void deleteById(int colorId);
}
