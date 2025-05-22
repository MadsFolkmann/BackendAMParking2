package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CaseDTORequest {
    private Long id;
    private LocalDate time;
    private String description;
    private Long userId;
}
