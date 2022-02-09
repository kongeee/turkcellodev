package com.turkcell.rentacar.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;

@Service
public class BrandManager implements BrandService {
	
	private BrandDao brandDao;
	
	@Autowired
	public BrandManager(BrandDao brandDao) {
		
		this.brandDao = brandDao;
	}

	@Override
	public List<Brand> getAll() {
		return this.brandDao.findAll();
	}

	@Override
	public void add(Brand brand) {
		this.brandDao.save(brand);
		
	}

}
