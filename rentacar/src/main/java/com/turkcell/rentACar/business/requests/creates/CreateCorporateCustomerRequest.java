package com.turkcell.rentACar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateCorporateCustomerRequest {
    
    @NotNull
	private String companyName;

    @NotNull
	private String taxNumber;
}
