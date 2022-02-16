package com.turkcell.rentacar.api.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.dtos.GetColorDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.entities.concretes.Color;

@RestController
@RequestMapping("/api/colors")
public class ColorsController {
	private ColorService colorService;

	public ColorsController(ColorService colorService) {
		
		this.colorService = colorService;
	}
	
	@GetMapping("/getall")
	public List<ColorListDto> getAll(){
		return this.colorService.getAll();
	}
	
	@PostMapping("/add")
	public void add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException {
		this.colorService.add(createColorRequest);
	}
	
	@GetMapping("/getbyid")
	public GetColorDto getById(int id) {
		return this.colorService.getById(id);
	}

}
