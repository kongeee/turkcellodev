package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;




@Data
@AllArgsConstructor
@NoArgsConstructor

public class UpdateIndividualCustomerRequest 
{
    @Positive
    private int individualCustomerId;

    @Email
    @NotNull
    private String email;
    
    @NotNull
    private String password;

    @NotNull
	private String firstName;
    
    @NotNull
	private String lastName;

    @NotNull
	private String nationalIdentity;

}