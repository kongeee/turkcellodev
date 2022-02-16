package com.turkcell.rentacar.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCarRequest {
	
	private int carId;
	private double dailyPrice;
	private int modelYear;
	private String description;
	private int brandId;
	private int colorId;

}
