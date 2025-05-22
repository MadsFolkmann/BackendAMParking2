package org.parking.backendamparking.Repository;

import org.parking.backendamparking.Entity.Cars;
import org.parking.backendamparking.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarsRepository extends JpaRepository<Cars, Long> {
    List<Cars> findAll();
    Cars findByregistrationNumber(String registrationNumber);
    List<Cars> findByUser(User user);
}
