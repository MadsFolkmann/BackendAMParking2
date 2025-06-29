package org.parking.backendamparking.Controller;


import jakarta.validation.Valid;
import org.parking.backendamparking.DTO.LoginRequest;
import org.parking.backendamparking.DTO.ParkingDTOResponse;
import org.parking.backendamparking.DTO.ParkingDTORequest;
import org.parking.backendamparking.DTO.UserDTOResponse;
import org.parking.backendamparking.Service.ParkingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/parking")
@CrossOrigin
public class ParkingController {


    private final ParkingService parkingService;


    public ParkingController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping()
    public List<ParkingDTOResponse> getAllParkings() {
        return parkingService.getAllParkings();
    }

    @GetMapping("/{id}")
    public ParkingDTOResponse getParkingById(@PathVariable Long id) {
        return parkingService.getParkingById(id);
    }

    @GetMapping("/plateNumber/{plateNumber}")
    public ParkingDTOResponse getParkingByPlateNumber(@PathVariable String plateNumber) {
        return parkingService.getParkingByPlateNumber(plateNumber);
    }

    @GetMapping("/active/user/{userId}")
    public List<ParkingDTOResponse> getActiveParkingsByUserId(@PathVariable Long userId) {
        return parkingService.getActiveParkingsByUserId(userId);
    }

    @GetMapping("/user/{userId}")
    public List<ParkingDTOResponse> getParkingsByUserId(@PathVariable Long userId) {
        return parkingService.getParkingsByUserId(userId);
    }

    @GetMapping("/user/{userId}/year/{year}")
    public List<ParkingDTOResponse> getParkingsByUserIdAndYear(@PathVariable Long userId, @PathVariable int year) {
        return parkingService.getParkingsByUserIdAndYear(userId, year);
    }

    @GetMapping("/active/plateNumber/{plateNumber}")
    public boolean hasActiveParkingByPlateNumber(@PathVariable String plateNumber) {
        return parkingService.hasActiveParkingByPlateNumber(plateNumber);
    }

    @PostMapping("/add")
    public ResponseEntity<ParkingDTOResponse> addParking(@Valid @RequestBody ParkingDTORequest parkingDTORequest) {
        ParkingDTOResponse savedParking = parkingService.addParking(parkingDTORequest);
        return ResponseEntity.status(201).body(savedParking);
    }

    @PutMapping("/{id}")
    public ParkingDTOResponse updateParking(@PathVariable Long id, @RequestBody ParkingDTORequest parkingDTORequest) {
        return parkingService.updateParking(id, parkingDTORequest);
    }

    @DeleteMapping("/{id}")
    public  ResponseEntity<Void>deleteParking(@PathVariable Long id) {
        parkingService.deleteParking(id);
        return ResponseEntity.noContent().build();
    }


}
