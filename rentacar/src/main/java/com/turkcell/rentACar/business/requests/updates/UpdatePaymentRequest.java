package com.turkcell.rentACar.business.requests.updates;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentRequest 
{
    @Positive
    private int paymentId;

    @Positive
    private int customerId;
    
    @Positive
    private int invoiceId;

    @Positive
    private double price;
}
