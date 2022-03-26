package com.turkcell.rentACar.core.utilities.bankServices;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankInformation 
{
    private String cardOwnerName;
    private String cardNumber;
    private String cardCVC;
    private String cardDate;  
}
