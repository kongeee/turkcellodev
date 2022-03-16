package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateCorporateCustomerRequest 
{
    @Positive
    private int corporateCustomerId;

    @NotNull
	private String companyName;

    @NotNull
	private String taxNumber;
}