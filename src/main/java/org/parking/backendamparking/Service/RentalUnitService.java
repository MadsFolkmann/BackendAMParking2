package org.parking.backendamparking.Service;

import org.parking.backendamparking.Repository.RentalUnitRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalUnitService {

    private final RentalUnitRepository rentalUnitRepository;

    public RentalUnitService(RentalUnitRepository rentalUnitRepository) {
        this.rentalUnitRepository = rentalUnitRepository;
    }

    public boolean checkRentalUnit(Long unitNumber) {
        return rentalUnitRepository.existsByUnitNumber(unitNumber);
    }
}