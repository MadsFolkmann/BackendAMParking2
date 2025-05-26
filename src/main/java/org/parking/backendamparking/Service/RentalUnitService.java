package org.parking.backendamparking.Service;

import org.parking.backendamparking.Repository.RentalUnitRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalUnitService {

    private final RentalUnitRepository rentalUnitRepository;

    public RentalUnitService(RentalUnitRepository rentalUnitRepository) {
        this.rentalUnitRepository = rentalUnitRepository;
    }

    /**
     * Check if a rental unit exists by its unit number.
     * @param unitNumber the unit number of the rental unit
     * @return true if the rental unit exists, false otherwise
     */

    public boolean checkRentalUnit(Long unitNumber) {
        return rentalUnitRepository.existsByUnitNumber(unitNumber);
    }
}