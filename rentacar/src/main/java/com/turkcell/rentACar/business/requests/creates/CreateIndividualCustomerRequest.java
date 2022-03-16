package com.turkcell.rentACar.business.requests.creates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;

import javax.validation.constraints.NotNull;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class CreateIndividualCustomerRequest 
{
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