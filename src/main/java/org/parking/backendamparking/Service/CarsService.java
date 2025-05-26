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

    /**
     * Get All Cars
     * @return List of CarsDTOResponse
     * This method retrieves all cars from the repository,
     */
    /* Get All Cars */
    public List<CarsDTOResponse> getAllCars() {
        List<Cars> allCars = carsRepository.findAll();
        return allCars.stream().map(CarsDTOResponse::new).collect(Collectors.toList());
    }

    /**
     * Get Cars By User ID
     * @param userId the ID of the user whose cars are to be retrieved
     * @return List of CarsDTOResponse
     */

    /* Get Cars By User ID */
    public List<CarsDTOResponse> getCarsByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Cars> cars = carsRepository.findByUser(user);
        return cars.stream().map(CarsDTOResponse::new).collect(Collectors.toList());
    }

    /**
     * Get Car By Plate Number
     * @param registrationNumber the registration number of the car to be retrieved
     * @return CarsDTOResponse
     */

    /* Get Car By Plate Number */
    public CarsDTOResponse getCarByPlateNumber(String registrationNumber) {
        Cars car = carsRepository.findByregistrationNumber(registrationNumber);
        return new CarsDTOResponse(car);
    }

    /**
     * Add a new car
     * @param request the request containing car details
     * @return CarsDTOResponse
     * This method creates a new car entity, sets its properties from the request,
     * associates it with a user, and saves it to the repository.
     */

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

    /**
     * Update an existing car
     * @param id the ID of the car to be updated
     * @param request the request containing updated car details
     * @return CarsDTOResponse
     * This method retrieves the car by ID, updates its properties from the request,
     * and saves it back to the repository.
     */

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

    /**
     * Delete a car by ID
     * @param id the ID of the car to be deleted
     * This method deletes the car from the repository by its ID.
     */

    /* Delete Car */
    public void deleteCar(Long id) {
        carsRepository.deleteById(id);
    }
}
