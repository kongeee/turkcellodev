package com.turkcell.rentACar.entities.concretes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@PrimaryKeyJoinColumn(name="corporate_customer_id",referencedColumnName = "customer_id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "corporate_customers")

public class CorporateCustomer extends Customer
{    
    @Column(name = "corporate_customer_id", insertable = false , updatable = false)
	private int corporateCustomerId;

    @Column(name = "company_name")
	private String companyName;
    
    @Column(name = "tax_number")
	private String taxNumber;
    
}