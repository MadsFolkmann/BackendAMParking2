package org.parking.backendamparking.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private int phoneNumber;
    @Column(nullable = false, unique = true)
    private Long rentalUnit;
    private String adress;
    private String city;
    private int zipCode;



    public User() {
    }

    public User(String name, String email, String password, int phoneNumber, Long rentalUnit, String adress, String city, int zipCode) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.rentalUnit = this.rentalUnit;
        this.adress = adress;
        this.city = city;
        this.zipCode = zipCode;
    }
}
