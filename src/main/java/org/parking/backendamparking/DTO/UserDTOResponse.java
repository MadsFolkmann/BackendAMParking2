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
    private String name;
    private String password;
    private String email;
    private String number;
    private Long lejemaal;
    private String adress;
    private String city;
    private int zipCode;

    public UserDTOResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.number = user.getNumber();
        this.lejemaal = user.getLejemaal();
        this.adress = user.getAdress();
        this.city = user.getCity();
        this.zipCode = user.getZipCode();
    }
}
