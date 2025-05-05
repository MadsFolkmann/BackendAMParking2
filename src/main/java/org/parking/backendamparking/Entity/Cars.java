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
    private String numberPlate;
    private String brand;
    private String model;
    private int year;
    private String color;
    private String type;
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Cars() {
    }

    public Cars(String numberPlate, String brand, String model, int year, String color, String type, String description) {
        this.numberPlate = numberPlate;
        this.brand = brand;
        this.model = model;
        this.year = year;
        this.color = color;
        this.type = type;
        this.description = description;
    }
}
