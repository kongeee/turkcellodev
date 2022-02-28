package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

import com.turkcell.rentacar.entities.concretes.Car;

public interface CarDao extends JpaRepository<Car, Integer> {
    List<Car> getByDailyPriceIsLessThanEqual(double dailyPrice);
}
