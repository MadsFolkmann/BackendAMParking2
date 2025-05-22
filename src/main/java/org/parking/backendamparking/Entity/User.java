package org.parking.backendamparking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.parking.backendamparking.Roles;

@Getter
@Setter
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(nullable = false, unique = true)
    private String email;
    private String password;
    private int phoneNumber;
    @Column(nullable = false, unique = true)
    private Long rentalUnit;
    private String address;
    private String city;
    private int zipCode;
    @Enumerated(EnumType.STRING)
    private Roles role;



    public User() {
    }

    public User(String firstName, String lastName, String email, String password, int phoneNumber, Long rentalUnit, String address, String city, int zipCode) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.rentalUnit = this.rentalUnit;
        this.address = address;
        this.city = city;
        this.zipCode = zipCode;
    }
}
