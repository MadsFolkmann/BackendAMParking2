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
    private String numberPlate;
    private String brand;
    private String model;
    private int year;
    private String color;
    private String type;
    private String description;
    private Long userId;

    public CarsDTOResponse(Cars cars) {
        this.id = cars.getId();
        this.numberPlate = cars.getNumberPlate();
        this.brand = cars.getBrand();
        this.model = cars.getModel();
        this.year = cars.getYear();
        this.color = cars.getColor();
        this.type = cars.getType();
        this.description = cars.getDescription();
        this.userId = cars.getUser().getId();

    }


}
