package com.turkcell.northwind.business.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {
	private String productName;
	private double unitPrice;
	private String quantityPerUnit;
	private int unitsInStock;
	private int categoryId;
}
