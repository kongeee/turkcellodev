package com.turkcell.rentACar.business.requests.updates;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerCardRequest 
{
    private int customerCardId;
    private String cardOwnerName;
    private String cardNumber;
    private String cardCVC;
    private String cardDate;
    private int customerId;

}
