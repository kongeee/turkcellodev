package com.turkcell.rentacar.api.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.ColorDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.exceptions.BusinessException;
import com.turkcell.rentacar.entities.concretes.Brand;
import com.turkcell.rentacar.entities.concretes.Color;

import lombok.AllArgsConstructor;

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
	
	@GetMapping("/getbyid")
	public ColorDto getById(@RequestParam(required = true) int colorId){
		return this.colorService.getById(colorId);
	}

	@PostMapping("/add")
	public void add(@RequestBody CreateColorRequest createColorRequest) throws BusinessException {
		
		this.colorService.add(createColorRequest);
	}
	
	@PostMapping("/update")
	public void update(@RequestBody UpdateColorRequest updateColorRequest) throws BusinessException {
		this.colorService.update(updateColorRequest);
	}
	
	@PostMapping("/deletebyid")
	public void deleteById(@RequestBody int colorId) {
		this.colorService.deleteById(colorId);
	}
	
	
	
}
