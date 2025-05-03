package org.parking.backendamparking.Controller;


import org.parking.backendamparking.DTO.ParkingDTOResponse;
import org.parking.backendamparking.Service.ParkingService;
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
    public ParkingDTOResponse getParkingById(Long id) {
        return parkingService.getParkingById(id);
    }

    @GetMapping("/plateNumber/{plateNumber}")
    public ParkingDTOResponse getParkingByPlateNumber(Long plateNumber) {
        return parkingService.getParkingByPlateNumber(plateNumber);
    }

    @GetMapping("/user/{userId}")
    public List<ParkingDTOResponse> getParkingsByUserId(Long userId) {
        return parkingService.getParkingsByUserId(userId);
    }

    @PostMapping(("/"))
    public ParkingDTOResponse addParking(@RequestBody ParkingDTOResponse parkingDTOResponse) {
        return parkingService.addParking(parkingDTOResponse);
    }

    @PutMapping("/{id}")
    public ParkingDTOResponse updateParking(@PathVariable Long id, @RequestBody ParkingDTOResponse parkingDTOResponse) {
        return parkingService.updateParking(id, parkingDTOResponse);
    }

    @DeleteMapping("/{id}")
    public void deleteParking(@PathVariable Long id) {
        parkingService.deleteParking(id);
    }


}
