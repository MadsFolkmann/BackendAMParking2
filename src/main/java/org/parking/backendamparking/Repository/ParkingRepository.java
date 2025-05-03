package org.parking.backendamparking.Repository;

import org.parking.backendamparking.Entity.Parking;
import org.parking.backendamparking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {
    Parking findByPlateNumber(Long plateNumber);
    List<Parking> findAll();

}

