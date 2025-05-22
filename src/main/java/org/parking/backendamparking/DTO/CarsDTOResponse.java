package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.parking.backendamparking.Entity.Cars;

@Getter
@Setter
@NoArgsConstructor
public class CarsDTOResponse {
    private Long id;
    private String registrationNumber;
    private String make;
    private String model;
    private int modelYear;
    private String color;
    private String type;
    private int totalWeight;
    private String description;
    private Long userId;

    public CarsDTOResponse(Cars cars) {
        this.id = cars.getId();
        this.registrationNumber = cars.getRegistrationNumber();
        this.make = cars.getMake();
        this.model = cars.getModel();
        this.modelYear = cars.getModelYear();
        this.color = cars.getColor();
        this.type = cars.getType();
        this.totalWeight = cars.getTotalWeight();
        this.description = cars.getDescription();
        this.userId = cars.getUser().getId();

    }


}
