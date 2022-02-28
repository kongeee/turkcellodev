package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.turkcell.rentacar.business.abstracts.ColorService;

import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.dtos.ColorByIdDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;

import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;

import com.turkcell.rentacar.entities.concretes.Color;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ColorManager implements ColorService {

	 private ColorDao colorDao;
	    private ModelMapperService modelMapperService;

	    @Override
	    public DataResult<List<ColorListDto>> getAll() {

	        List<Color> result = this.colorDao.findAll();
	        List<ColorListDto> response = result.stream()
	                .map(color -> this.modelMapperService.forDto().map(color, ColorListDto.class))
	                .collect(Collectors.toList());

	        return new SuccessDataResult<List<ColorListDto>>(response, "Colors are listed successfully.");

	    }

	    @Override
	    public Result add(CreateColorRequest createColorRequest) throws BusinessException {
	        Color color = this.modelMapperService.forRequest().map(createColorRequest, Color.class);

	        checkIfColorNameIsUnique(color.getColorName());


	        this.colorDao.save(color);

	        return new SuccessResult("Color is added.");
	    }

	    @Override
	    public DataResult<ColorByIdDto> getById(int colorId) {
	        Color color = this.colorDao.getById(colorId);

	        ColorByIdDto response = this.modelMapperService.forDto().map(color, ColorByIdDto.class);

	        return new SuccessDataResult<ColorByIdDto>(response);
	    }


	    @Override
	    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
	        Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);

	        checkIfColorNameIsUnique(color.getColorName());

	        this.colorDao.save(color);

	        return new SuccessResult("Color is updated.");
	    }

	    @Override
	    public Result deleteById(int colorId) {
	        this.colorDao.deleteById(colorId);
	        return new SuccessResult("Color is deleted.");

	    }

	    private boolean checkIfColorNameIsUnique(String colorName) throws BusinessException {

	        for (ColorListDto colorElement : this.getAll().getData()) {
	            if (colorElement.getColorName().equals(colorName)) {
	                throw new BusinessException("Aynı isimde birden fazla renk olamaz");
	            }
	        }

	        return true;

	    }

}
