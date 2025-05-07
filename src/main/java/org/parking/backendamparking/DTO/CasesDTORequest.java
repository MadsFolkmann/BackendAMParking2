package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.Setter;
import org.parking.backendamparking.Entity.User;

import java.time.LocalDate;

@Getter
@Setter
public class CasesDTORequest {
    private Long id;
    private LocalDate time;
    private User user;
    private String description;
}
