package com.turkcell.rentACar.business.requests.creates;


import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateInvoiceRequest {
    
    @NotNull
	private LocalDate creationDate;

    @NotNull
    @Min(0)
    private double totalPrice;

    @Positive
    private int carRentalId;

    @Positive
    private int customerId;
    
}
