package org.parking.backendamparking.Service;

import org.parking.backendamparking.DTO.ParkingDTORequest;
import org.parking.backendamparking.DTO.ParkingDTOResponse;
import org.parking.backendamparking.Entity.Parking;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.ParkingRepository;
import org.parking.backendamparking.Repository.UserRepository;
import org.springframework.stereotype.Service;

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

    /* Get All Parkings */
    public List<ParkingDTOResponse> getAllParkings() {
        List<Parking> allParkings = parkingRepository.findAll();
        return allParkings.stream()
                .map(ParkingDTOResponse::new)
                .collect(Collectors.toList());
    }

    /* Get Parking By Plate Number */
    public ParkingDTOResponse getParkingByPlateNumber(String plateNumber) {
        Parking parking = parkingRepository.findByPlateNumber(plateNumber);
        return new ParkingDTOResponse(parking);
    }


    /* Get Parking By User ID */
    public List<ParkingDTOResponse> getParkingsByUserId(Long userId) {
        List<Parking> parkings = parkingRepository.findAll().stream()
                .filter(parking -> parking.getUser() != null && parking.getUser().getId().equals(userId))
                .collect(Collectors.toList());
        return parkings.stream()
                .map(ParkingDTOResponse::new)
                .collect(Collectors.toList());
    }

    /* Get Parking By Parking Id */
    public ParkingDTOResponse getParkingById(Long id) {
        Parking parking = parkingRepository.findById(id).orElse(null);
        return new ParkingDTOResponse(parking);
    }

    /* Add Parking */
    public ParkingDTOResponse addParking(ParkingDTORequest request) {
        Parking newParking = new Parking();
        newParking.setPArea(request.getPArea());
        newParking.setPlateNumber(request.getPlateNumber());
        newParking.setStartTime(request.getStartTime());
        newParking.setEndTime(request.getEndTime());
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        newParking.setUser(user);
        parkingRepository.save(newParking);
        return new ParkingDTOResponse(newParking);
    }

    /* Update Parking */
    public ParkingDTOResponse updateParking(Long id, ParkingDTOResponse request) {
        Parking parking = parkingRepository.findById(id).orElseThrow();
        parking.setPArea(request.getPArea());
        parking.setPlateNumber(request.getPlateNumber());
        parking.setStartTime(request.getStartTime());
        parking.setEndTime(request.getEndTime());
        User user = userRepository.findById(request.getUserId()).orElseThrow();
        parking.setUser(user);
        parkingRepository.save(parking);
        return new ParkingDTOResponse(parking);
    }
    /* Delete Parking */

    public void deleteParking(Long id) {
        Parking parking = parkingRepository.findById(id).orElseThrow();
        parkingRepository.delete(parking);
    }
}
