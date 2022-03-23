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
@Table(name = "cities")
public class City 
{
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "city_id")
	private int cityId;

	@Column(name = "city_name")
	private String cityName;

	@OneToMany(mappedBy = "startCity")
	private List<CarRental> startCity;

    @OneToMany(mappedBy = "endCity")
	private List<CarRental> endCity;
}
