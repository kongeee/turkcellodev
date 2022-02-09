package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.entities.concretes.Color;

public interface ColorService {
	List<Color> getAll();
	void add(Color color);
}
