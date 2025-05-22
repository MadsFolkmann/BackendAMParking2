package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.Setter;
import org.parking.backendamparking.Roles;

@Getter
@Setter
public class UserDTORequest {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private int phoneNumber;
    private Long rentalUnit;
    private String adress;
    private String city;
    private int zipCode;
    private Roles role;

}
