package com.turkcell.rentACar.business.requests.deletes;

import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class DeleteInvoiceRequest 
{
    @Positive
    private int invoiceId;
}