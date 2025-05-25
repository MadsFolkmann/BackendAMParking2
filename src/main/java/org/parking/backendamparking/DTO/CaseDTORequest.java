package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CaseDTORequest {
    private Long id;
    private String plateNumber;
    private LocalDate time;
    private String description;
    private Boolean done;
    private Long userId;
}
