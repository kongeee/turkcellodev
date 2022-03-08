package com.turkcell.rentACar.dataAccess.abstracts;

import com.turkcell.rentACar.entities.concretes.CarMaintenance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CarMaintenanceDao extends JpaRepository<CarMaintenance,Integer> {
    List<CarMaintenance> getAllByCar_CarId(Integer id);
    CarMaintenance getByCar_CarIdAndReturnDate(int carId,Date returnDate);
}
