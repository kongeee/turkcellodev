package com.turkcell.rentACar.entities.concretes;

import java.time.LocalDate;

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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_rental")
public class CarRental {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "carRental_id")
    private int carRentalId;

    @Column(name = "start_date")
    private LocalDate startDate;
	
    @Column(name = "return_date")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name="car_id")
    private Car car;
}
