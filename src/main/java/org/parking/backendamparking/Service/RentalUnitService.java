package org.parking.backendamparking.Service;

import org.parking.backendamparking.Repository.RentalUnitRepository;
import org.parking.backendamparking.Repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class RentalUnitService {

    private final RentalUnitRepository rentalUnitRepository;
    private final UserRepository userRepository;

    public RentalUnitService(RentalUnitRepository rentalUnitRepository, UserRepository userRepository) {
        this.rentalUnitRepository = rentalUnitRepository;
        this.userRepository = userRepository;
    }

    /**
     * Check if a rental unit exists by its unit number and is not already assigned to a user.
     * @param unitNumber the unit number of the rental unit
     * @return true if the rental unit exists and is not assigned to a user, false otherwise
     */
    public boolean checkRentalUnit(Long unitNumber) {
        boolean rentalUnitExists = rentalUnitRepository.existsByUnitNumber(unitNumber);
        boolean userExistsWithRentalUnit = userRepository.existsByRentalUnit(unitNumber);
        return rentalUnitExists && !userExistsWithRentalUnit;
    }
}

