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
    /* Delete Parking */

    public void deleteParking(Long id) {
        Parking parking = parkingRepository.findById(id).orElseThrow();
        parkingRepository.delete(parking);
    }

    public List<ParkingDTOResponse> getParkingsByUserIdAndYear(Long userId, int year) {
        List<Parking> parkings = parkingRepository.findAll().stream()
                .filter(parking -> parking.getUser() != null && parking.getUser().getId().equals(userId))
                .filter(parking -> parking.getStartTime().getYear() == year)
                .collect(Collectors.toList());
        return parkings.stream()
                .map(ParkingDTOResponse::new)
                .collect(Collectors.toList());
    }

    public List<ParkingDTOResponse> getActiveParkingsByUserId(Long userId) {
        LocalDateTime now = LocalDateTime.now();
        List<Parking> activeParkings = parkingRepository.findByUserIdAndEndTimeAfterOrEndTimeIsNull(userId, now);
        return activeParkings.stream()
                .map(ParkingDTOResponse::new)
                .collect(Collectors.toList());
    }
}
