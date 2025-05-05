package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarsDTORequest {
    private Long id;
    private String numberPlate;
    private String brand;
    private String model;
    private int year;
    private String color;
    private String type;
    private String description;
    private Long userId;

    public CarsDTORequest() {
    }

    public CarsDTORequest(String numberPlate, String brand, String model, int year, String color, String type, String description) {
        this.numberPlate = numberPlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.type = type;
        this.description = description;
    }
}
