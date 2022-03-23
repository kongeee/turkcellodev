package com.turkcell.rentACar.business.requests.creates;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePaymentRequest 
{
    @Positive
    private int customerId;
    
    @Positive
    private int invoiceId;

    @Positive
    private double price;
}
