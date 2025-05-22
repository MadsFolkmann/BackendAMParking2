package org.parking.backendamparking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String registrationNumber;
    private String make;
    private String model;
    private int modelYear;
    private String color;
    private String type;
    private int totalWeight;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Cars() {
    }

    public Cars(Cars cars) {
        this.registrationNumber = cars.getRegistrationNumber();
        this.make = cars.getMake();
        this.model = cars.getModel();
        this.modelYear = cars.getModelYear();
        this.color = cars.getColor();
        this.type = cars.getType();
        this.totalWeight = cars.getTotalWeight();
        this.description = cars.getDescription();
    }
}
