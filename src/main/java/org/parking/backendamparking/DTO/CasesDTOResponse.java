package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.parking.backendamparking.Entity.Cases;
import org.parking.backendamparking.Entity.User;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CasesDTOResponse {
    private Long id;
    private LocalDate time;
    private User user;
    private String description;


    public CasesDTOResponse(Cases cases){
        this.id = cases.getId();
        this.time = cases.getTime();
        this.description = cases.getDescription();
    }
}
