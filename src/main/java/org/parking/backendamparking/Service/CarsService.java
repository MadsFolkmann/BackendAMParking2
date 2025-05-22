package org.parking.backendamparking.Service;

import org.parking.backendamparking.DTO.CarsDTORequest;
import org.parking.backendamparking.DTO.CarsDTOResponse;
import org.parking.backendamparking.Entity.Cars;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.CarsRepository;
import org.parking.backendamparking.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarsService {

    private final CarsRepository carsRepository;
    private final UserRepository userRepository;

    public CarsService(CarsRepository carsRepository, UserRepository userRepository) {
        this.carsRepository = carsRepository;
        this.userRepository = userRepository;
    }

    /* Get All Cars */
    public List<CarsDTOResponse> getAllCars() {
        List<Cars> allCars = carsRepository.findAll();
        return allCars.stream().map(CarsDTOResponse::new).collect(Collectors.toList());
    }

    /* Get Cars By User ID */
    public List<CarsDTOResponse> getCarsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cars> cars = carsRepository.findByUser(user);
        return cars.stream().map(CarsDTOResponse::new).collect(Collectors.toList());
    }

    /* Get Car By Plate Number */
    public CarsDTOResponse getCarByPlateNumber(String registrationNumber) {
        Cars car = carsRepository.findByregistrationNumber(registrationNumber);
        return new CarsDTOResponse(car);
    }

    /* Add Car */
    public CarsDTOResponse addCar(CarsDTORequest request) {
        Cars newCar = new Cars();


        newCar.setRegistrationNumber(request.getRegistrationNumber());
        newCar.setMake(request.getMake());
        newCar.setModel(request.getModel());
        newCar.setModelYear(request.getModelYear());
        newCar.setColor(request.getColor());
        newCar.setType(request.getType());
        newCar.setTotalWeight(request.getTotalWeight());
        newCar.setDescription(request.getDescription());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        newCar.setUser(user);

        carsRepository.save(newCar);

        return new CarsDTOResponse(newCar);
    }

    /* Update Car */
    public CarsDTOResponse updateCar(Long id, CarsDTORequest request) {
        Cars car = carsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setRegistrationNumber(request.getRegistrationNumber());
        car.setMake(request.getMake());
        car.setModel(request.getModel());
        car.setModelYear(request.getModelYear());
        car.setColor(request.getColor());
        car.setType(request.getType());
        car.setTotalWeight(request.getTotalWeight());
        car.setDescription(request.getDescription());

        carsRepository.save(car);
        return new CarsDTOResponse(car);
    }

    /* Delete Car */
    public void deleteCar(Long id) {
        carsRepository.deleteById(id);
    }
}
