package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.entities.concretes.Brand;

public interface BrandService {
	List<Brand> getAll();
	void add (Brand brand);
}
