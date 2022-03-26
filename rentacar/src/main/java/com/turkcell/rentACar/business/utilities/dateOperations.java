package com.turkcell.rentACar.business.utilities;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class dateOperations 
{
    public static long findTheNumberOfDaysToRent(LocalDate startDate, LocalDate returnDate)
	{
		long days = ChronoUnit.DAYS.between(startDate, returnDate);
		
		return (days==0) ? 1 : days;
	}
}
