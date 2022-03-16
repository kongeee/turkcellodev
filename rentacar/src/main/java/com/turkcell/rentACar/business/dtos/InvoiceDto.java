package com.turkcell.rentACar.business.dtos;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto 
{    
	private int invoiceId;
	private LocalDate creationDate;
    private double totalPrice;
    private LocalDate startDate;
    private LocalDate returnDate;
    private long rentedDays;
}