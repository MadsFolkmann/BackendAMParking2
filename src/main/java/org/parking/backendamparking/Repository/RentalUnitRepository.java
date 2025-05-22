package org.parking.backendamparking.Repository;


import org.parking.backendamparking.Entity.RentalUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalUnitRepository extends JpaRepository<RentalUnit, Long> {
    boolean existsByUnitNumber(Long unitNumber);
}

