package com.turkcell.rentACar.business.concretes;

import com.turkcell.rentACar.business.abstracts.CarService;
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
    public ColorManager(ColorDao colorDao,ModelMapperService modelMapperService,CarService carService) {

        this.colorDao = colorDao;
        this.modelMapperService = modelMapperService;
    }

    @Override
    public DataResult<List<ColorListDto>> getAll() {
        List<Color> result = this.colorDao.findAll();
        if(result.isEmpty())
            return new ErrorDataResult<List<ColorListDto>>(null,"Current List is Empty");
        List<ColorListDto> response = result.stream().map(color->this.modelMapperService.forDto().map(color,ColorListDto.class)).collect(Collectors.toList());
        return new SuccessDataResult<List<ColorListDto>>(response,"Colors Listed Successfully");
    }

    @Override
    public Result add(CreateColorRequest createColorRequest) throws BusinessException {
        Color color = this.modelMapperService.forRequest().map(createColorRequest,Color.class);
        checkIfSameColor(color);
        this.colorDao.save(color);
        return new SuccessResult("Color Added Successfully");
    }

    @Override
    public DataResult<ColorDto> getById(int id) {
        if(this.colorDao.getAllByColorId(id).stream().count()==0)
            return new ErrorDataResult(null,"Color Does Not Exist");
        Color color = colorDao.getById(id);
        ColorDto colorDto = this.modelMapperService.forDto().map(color,ColorDto.class);
        return new SuccessDataResult<ColorDto>(colorDto,"Color Listed Successfully");
    }

    @Override
    public Result update(UpdateColorRequest updateColorRequest) throws BusinessException{
        if(this.colorDao.getAllByColorId(updateColorRequest.getColorId()).stream().count()==0)
            return new ErrorResult("Color Does Not Exist");
        Color color = colorDao.getById(updateColorRequest.getColorId());
        checkIfSameColor(color);
        color = this.modelMapperService.forRequest().map(updateColorRequest,Color.class);
        this.colorDao.save(color);
        return new SuccessResult("Color Updated Succesfully");
    }

    @Override
    public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException{
        if(this.colorDao.getAllByColorId(deleteColorRequest.getColorId()).stream().count()==0)
            return new ErrorResult("Brand Does Not Exist");
        Color color = this.modelMapperService.forRequest().map(deleteColorRequest,Color.class);
        this.colorDao.delete(color);
        return new SuccessResult("Color Deleted Succesfully");
    }

    void checkIfSameColor(Color color) throws BusinessException {
        if(this.colorDao.getAllByColorName(color.getColorName()).stream().count()!=0)
            throw new BusinessException("This brand already exists");
    }
}
