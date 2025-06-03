package org.parking.backendamparking.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.parking.backendamparking.Entity.PArea;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ParkingDTORequest {
    @NotBlank
    private PArea parea;

    @NotBlank
    private String plateNumber;
    private String carColor;
    private String carBrand;
    private String carModel;

    @NotBlank
    private LocalDateTime startTime;

    @NotBlank
    private LocalDateTime endTime;
    private Long userId;
}
