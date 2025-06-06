package org.parking.backendamparking.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.parking.backendamparking.Roles;

@Getter
@Setter
public class UserDTORequest {
    @NotBlank(message = "Firstname cannot be blank")
    private String firstName;

    @NotBlank(message = "Lastname cannot be blank")

    private String lastName;

    private String password;

    @Email(message = "Email must be valid")
    private String email;


    private int phoneNumber;

    private Long rentalUnit;

    private String address;

    private String city;

    private int zipCode;

    private Roles role;

}
