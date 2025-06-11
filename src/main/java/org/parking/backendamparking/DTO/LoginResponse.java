package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private UserDTOResponse user;

    public LoginResponse(String token, UserDTOResponse user) {
        this.token = token;
        this.user = user;
    }

    // getters and setters

}
