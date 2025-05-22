package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.Setter;
import org.parking.backendamparking.Roles;

@Getter
@Setter
public class UserUpdateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private String address;
    private String city;
    private int zipCode;
    private Roles role;
}