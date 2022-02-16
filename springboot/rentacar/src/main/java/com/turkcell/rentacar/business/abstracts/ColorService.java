package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.dtos.GetColorDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;


public interface ColorService {
	List<ColorListDto> getAll();
	void add(CreateColorRequest createColorRequest) throws BusinessException;
	GetColorDto getById(int id);
}
