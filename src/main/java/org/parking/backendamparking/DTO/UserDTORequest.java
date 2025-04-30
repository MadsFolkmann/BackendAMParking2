package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class UserDTORequest {
    private Long id;
    private String name;
    private String password;
}
