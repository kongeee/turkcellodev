package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateCorporateCustomerRequest {
    @NotNull
    private int corporateCustomerId;

    @NotNull
	private String companyName;

    @NotNull
	private String taxNumber;
}
