package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.ColorByIdDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.UpdateBrandRequest;
import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Color;

public interface ColorService {

	DataResult<List<ColorListDto>> getAll();

    Result add(CreateColorRequest createColorRequest) throws BusinessException;

    DataResult<ColorByIdDto> getById(int id);

    Result update(UpdateColorRequest updateColorRequest) throws BusinessException;

    Result deleteById(int colorId);
}
