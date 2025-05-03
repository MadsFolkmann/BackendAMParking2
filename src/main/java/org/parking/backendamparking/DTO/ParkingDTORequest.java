package org.parking.backendamparking.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ParkingDTORequest {
    private String pArea;
    private Long plateNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long userId;
}
