package com.turkcell.rentACar.business.abstracts;

import com.turkcell.rentACar.business.dtos.ColorDto;
import com.turkcell.rentACar.business.dtos.ColorListDto;
import com.turkcell.rentACar.business.requests.creates.CreateColorRequest;
import com.turkcell.rentACar.business.requests.deletes.DeleteColorRequest;
import com.turkcell.rentACar.business.requests.updates.UpdateColorRequest;
import com.turkcell.rentACar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentACar.core.utilities.results.DataResult;
import com.turkcell.rentACar.core.utilities.results.Result;

import java.util.List;

public interface ColorService {
	DataResult<List<ColorListDto>> getAll();
	Result add(CreateColorRequest createColorRequest) throws BusinessException;
	DataResult<ColorDto> getById(int id);
	Result update(UpdateColorRequest updateColorRequest) throws BusinessException;
	Result delete(DeleteColorRequest DeleteColorRequest) throws BusinessException;
}
