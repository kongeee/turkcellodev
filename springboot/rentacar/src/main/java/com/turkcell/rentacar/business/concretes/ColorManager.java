package com.turkcell.rentacar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentacar.entities.concretes.Color;

@Service
public class ColorManager implements ColorService{
	
	private ColorDao colorDao;
	
	
	@Autowired
	public ColorManager(ColorDao colorDao) {
		
		this.colorDao = colorDao;
	}

	@Override
	public List<Color> getAll() {
		return this.colorDao.findAll();
	}

	@Override
	public void add(Color color) {
		this.colorDao.save(color);
		
	}
	
}
