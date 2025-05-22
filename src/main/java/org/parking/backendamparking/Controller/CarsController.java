package org.parking.backendamparking.Controller;

import org.parking.backendamparking.DTO.CarsDTORequest;
import org.parking.backendamparking.DTO.CarsDTOResponse;
import org.parking.backendamparking.Service.CarsService;
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

    @GetMapping("/user/{userId}")
    public List<CarsDTOResponse> getCarsByUserId(@PathVariable Long userId) {
        return carsService.getCarsByUserId(userId);
    }

    @PostMapping("/add")
    public CarsDTOResponse addCar(@RequestBody CarsDTORequest request) {
        return carsService.addCar(request);
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
