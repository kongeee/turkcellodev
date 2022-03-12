package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "car_id")
	private int carId;

	@Column(name = "car_dailyprice")
	private double carDailyPrice;

	@Column(name = "car_modelyear")
	private int carModelYear;

	@Column(name = "car_description")
	private String carDescription;

	@ManyToOne
	@JoinColumn(name = "brand_id")
	private Brand brand;

	@ManyToOne
	@JoinColumn(name = "color_id")
	private Color color;

	@OneToMany(mappedBy = "car")
	private List<CarMaintenance> carMaintenance;

	@OneToMany(mappedBy = "car")
	private List<CarRental> carRental;
}
