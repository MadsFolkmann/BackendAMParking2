package org.parking.backendamparking.DTO;

import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;
import org.parking.backendamparking.Entity.PArea;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ParkingDTORequest {
    private PArea parea;
    private String plateNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long userId;
}
