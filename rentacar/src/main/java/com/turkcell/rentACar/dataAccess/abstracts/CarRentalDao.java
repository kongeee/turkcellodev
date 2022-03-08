package com.turkcell.rentACar.dataAccess.abstracts;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import com.turkcell.rentACar.entities.concretes.CarRental;

@Repository
public interface CarRentalDao extends JpaRepository<CarRental,Integer> {
    List<CarRental> getAllByCar_CarId(Integer id);
    
  //  @Query("From CarRental where startDate<=:date and returnDate=>:date and carId=:carId")
  //  List<CarRental> getCarRental(Date date,Integer carId);
    
    CarRental getByCar_CarIdAndReturnDate(int carId,Date returnDate);
}
