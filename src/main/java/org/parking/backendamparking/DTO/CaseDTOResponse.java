package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.parking.backendamparking.Entity.Case;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class CaseDTOResponse {
    private Long id;
    private String plateNumber;
    private LocalDate time;
    private String description;
    private Boolean done;
    private Long userId;
    private UserDTOResponse user;


    public CaseDTOResponse(Case aCase){
        this.id = aCase.getId();
        this.plateNumber = aCase.getPlateNumber();
        this.time = aCase.getTime();
        this.description = aCase.getDescription();
        this.done = aCase.getDone();
        this.userId = aCase.getUser().getId();
        this.user = new UserDTOResponse(aCase.getUser());
    }
}
