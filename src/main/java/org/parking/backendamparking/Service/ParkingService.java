package org.parking.backendamparking.Service;

import org.parking.backendamparking.DTO.ParkingDTORequest;
import org.parking.backendamparking.DTO.ParkingDTOResponse;
import org.parking.backendamparking.Entity.Parking;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.ParkingRepository;
import org.parking.backendamparking.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service

public class ParkingService {
    private final ParkingRepository parkingRepository;
    private final UserRepository userRepository;


    public ParkingService(ParkingRepository parkingRepository, UserRepository userRepository) {
        this.parkingRepository = parkingRepository;
        this.userRepository = userRepository;
    }

    /**
     * Get All Parkings
     * @return List of ParkingDTOResponse
     */

    /* Get All Parkings */
    public List<ParkingDTOResponse> getAllParkings() {
        List<Parking> allParkings = parkingRepository.findAll();
        return allParkings.stream()
                .map(ParkingDTOResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get Parking By Plate Number
     * @param plateNumber the plate number of the parking to be retrieved
     * @return ParkingDTOResponse
     */

    /* Get Parking By Plate Number */
    public ParkingDTOResponse getParkingByPlateNumber(String plateNumber) {
        Parking parking = parkingRepository.findByPlateNumber(plateNumber);
        return new ParkingDTOResponse(parking);
    }

    /**
     * Get Parkings By User ID
     * @param userId the ID of the user whose parkings are to be retrieved
     * @return List of ParkingDTOResponse
     */


    /* Get Parking By User ID */
    public List<ParkingDTOResponse> getParkingsByUserId(Long userId) {
        List<Parking> parkings = parkingRepository.findAll().stream()
                .filter(parking -> parking.getUser() != null && parking.getUser().getId().equals(userId))
                .collect(Collectors.toList());
        return parkings.stream()
                .map(ParkingDTOResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get Parking By Parking Id
     * @param id the ID of the parking to be retrieved
     * @return ParkingDTOResponse
     */

    /* Get Parking By Parking Id */
    public ParkingDTOResponse getParkingById(Long id) {
        Parking parking = parkingRepository.findById(id).orElse(null);
        return new ParkingDTOResponse(parking);
    }

    /**
     * Check if there is an active parking by plate number
     * @param plateNumber the plate number to check for active parking
     * @return boolean indicating if there is an active parking
     */

    public boolean hasActiveParkingByPlateNumber(String plateNumber) {
        LocalDateTime now = LocalDateTime.now();
        List<Parking> activeParkings = parkingRepository.findByPlateNumberAndEndTimeAfterOrPlateNumberAndEndTimeIsNull(plateNumber, now, plateNumber);
        return !activeParkings.isEmpty();
    }

    /**
     * Add Parking
     * @param request the ParkingDTORequest containing the details of the parking to be added
     * @return ParkingDTOResponse
     */

    /* Add Parking */
    public ParkingDTOResponse addParking(ParkingDTORequest request) {
        Parking newParking = new Parking();
        newParking.setParea(request.getParea());
        newParking.setPlateNumber(request.getPlateNumber());
        newParking.setCarBrand(request.getCarBrand());
        newParking.setCarModel(request.getCarModel());
        newParking.setCarColor(request.getCarColor());
        newParking.setStartTime(request.getStartTime());
        newParking.setEndTime(request.getEndTime());
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        newParking.setUser(user);
        parkingRepository.save(newParking);
        return new ParkingDTOResponse(newParking);
    }

    /**
     * Update Parking
     * @param id the ID of the parking to be updated
     * @param request the ParkingDTORequest containing the updated details
     * @return ParkingDTOResponse
     */

    /* Update Parking */
    public ParkingDTOResponse updateParking(Long id, ParkingDTORequest request) {
        Parking parking = parkingRepository.findById(id).orElseThrow();
        parking.setParea(request.getParea());
        parking.setPlateNumber(request.getPlateNumber());
        parking.setCarBrand(request.getCarBrand());
        parking.setCarModel(request.getCarModel());
        parking.setCarColor(request.getCarColor());
        parking.setStartTime(request.getStartTime());
        parking.setEndTime(request.getEndTime());
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        parking.setUser(user);
        parkingRepository.save(parking);
        return new ParkingDTOResponse(parking);
    }

    /**
     * Delete Parking
     * @param id the ID of the parking to be deleted
     */

    /* Delete Parking */
    public void deleteParking(Long id) {
        Parking parking = parkingRepository.findById(id).orElseThrow();
        parkingRepository.delete(parking);
    }

    /**
     * Get Parkings by User ID and Year
     * @param userId the ID of the user whose parkings are to be retrieved
     * @param year the year for which parkings are to be retrieved
     * @return List of ParkingDTOResponse
     */

    public List<ParkingDTOResponse> getParkingsByUserIdAndYear(Long userId, int year) {
        List<Parking> parkings = parkingRepository.findAll().stream()
                .filter(parking -> parking.getUser() != null && parking.getUser().getId().equals(userId))
                .filter(parking -> parking.getStartTime().getYear() == year)
                .collect(Collectors.toList());
        return parkings.stream()
                .map(ParkingDTOResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Get Active Parkings by User ID
     * @param userId the ID of the user whose active parkings are to be retrieved
     * @return List of ParkingDTOResponse
     */

    public List<ParkingDTOResponse> getActiveParkingsByUserId(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        List<Parking> activeParkings = parkingRepository.findByUserIdAndEndTimeAfterOrEndTimeIsNull(userId, now);
        return activeParkings.stream()
                .map(ParkingDTOResponse::new)
                .collect(Collectors.toList());
    }
}
