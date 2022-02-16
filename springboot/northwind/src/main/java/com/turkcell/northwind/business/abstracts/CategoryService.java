package com.turkcell.northwind.business.abstracts;

import java.util.List;

import com.turkcell.northwind.business.dtos.CategoryListDto;
import com.turkcell.northwind.business.requests.CreateCategoryRequest;


public interface CategoryService {
	List<CategoryListDto> getAll();
	void add(CreateCategoryRequest createCategoryRequest);
}
