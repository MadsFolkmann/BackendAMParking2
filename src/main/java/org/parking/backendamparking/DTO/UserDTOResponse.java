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
    private String lejemaal;

    public UserDTOResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.password = user.getPassword();
        this.email = user.getEmail();
        this.number = user.getNumber();
        this.lejemaal = user.getLejemaal();

    }
}
