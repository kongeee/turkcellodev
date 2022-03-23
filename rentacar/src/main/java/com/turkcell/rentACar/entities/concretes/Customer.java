package com.turkcell.rentACar.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.turkcell.rentACar.core.entities.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name="customer_id",referencedColumnName = "user_id")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customers")
public class Customer extends User
{
	@Column(name = "customer_id", insertable = false , updatable = false)
	private int customerId;

    @OneToMany(mappedBy = "customer")
    private List<CarRental> carRentals;
    
    @OneToMany(mappedBy = "customer")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "customer")
    private List<Payment> payments;

}