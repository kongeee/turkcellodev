package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "invoices")
public class Invoice 
{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invoice_id")
	private int invoiceId;

	@Column(name = "creation_date")
	private LocalDate creationDate;
    
    @Column(name = "total_price")
    private double totalPrice;
    
    @OneToOne()
    @JoinColumn(name = "car_rental_id" )
    private CarRental carRental;

    @ManyToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne(mappedBy = "invoice")
    private Payment payment;
}