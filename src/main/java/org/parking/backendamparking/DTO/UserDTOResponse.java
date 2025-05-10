package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.parking.backendamparking.Entity.User;

@Getter
@Setter
@NoArgsConstructor
public class UserDTOResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private int phoneNumber;
    private Long rentalUnit;
    private String adress;
    private String city;
    private int zipCode;

    public UserDTOResponse(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.phoneNumber = user.getPhoneNumber();
        this.rentalUnit = user.getRentalUnit();
        this.adress = user.getAdress();
        this.city = user.getCity();
        this.zipCode = user.getZipCode();
    }
}
