package com.turkcell.rentACar.entities.concretes;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer_cards")
@Entity
public class CustomerCard {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customer_card_id")
	private int customerCardId;

	@Column(name = "card_owner_name")
	private String cardOwnerName;

	@Column(name = "card_number")
	private String cardNumber;

	@Column(name = "card_cvc")
	private String cardCVC;

	@Column(name = "card_date")
	private String cardDate;  

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

    
}
