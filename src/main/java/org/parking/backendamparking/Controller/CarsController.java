package org.parking.backendamparking.Controller;

import jakarta.validation.Valid;
import org.parking.backendamparking.DTO.CarsDTORequest;
import org.parking.backendamparking.DTO.CarsDTOResponse;
import org.parking.backendamparking.Service.CarsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
@CrossOrigin
public class CarsController {

    private final CarsService carsService;

    public CarsController(CarsService carsService) {
        this.carsService = carsService;
    }

    @GetMapping
    public List<CarsDTOResponse> getAllCars() {
        return carsService.getAllCars();
    }

    @GetMapping("/plate/{registration_number}")
    public CarsDTOResponse getCarByPlateNumber(@PathVariable String registrationNumber) {
        return carsService.getCarByPlateNumber(registrationNumber);
    }

    @GetMapping("/{id}")
    public CarsDTOResponse getCarById(@PathVariable Long id) {
        return carsService.getCarById(id);
    }

    @GetMapping("/user/{userId}")
    public List<CarsDTOResponse> getCarsByUserId(@PathVariable Long userId) {
        return carsService.getCarsByUserId(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<CarsDTOResponse> addCar(@RequestBody CarsDTORequest carRequest) {
        CarsDTOResponse savedCar = carsService.addCar(carRequest);
        return ResponseEntity.status(201).body(savedCar);
    }

    @PutMapping("/{id}")
    public CarsDTOResponse updateCar(@PathVariable Long id, @RequestBody CarsDTORequest request) {
        return carsService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCar(@PathVariable Long id) {
        carsService.deleteCar(id);
    }
}
