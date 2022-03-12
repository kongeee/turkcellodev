package com.turkcell.rentACar.entities.concretes;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "car_maintenances")
public class CarMaintenance {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "maintenance_id")
	private int maintenanceId;

	@Column(name = "maintenance_description")
	private String maintenanceDescription;

	@Column(name = "return_date")
	private LocalDate returnDate;

	@ManyToOne
	@JoinColumn(name = "car_id")
	private Car car;
}
