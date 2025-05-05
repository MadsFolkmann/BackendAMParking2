package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTORequest {
    private String name;
    private String password;
    private String email;
    private String number;
    private Long rentalUnit;
    private String adress;
    private String city;
    private int zipCode;
}
