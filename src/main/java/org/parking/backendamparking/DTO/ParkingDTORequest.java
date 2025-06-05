package org.parking.backendamparking.DTO;

import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.parking.backendamparking.Entity.PArea;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ParkingDTORequest {
    @NotNull(message = "PArea cannot be null")
    private PArea parea;

    @NotBlank
    private String plateNumber;
    private String carColor;
    private String carBrand;
    private String carModel;

    private LocalDateTime startTime;

    private LocalDateTime endTime;
    private Long userId;
}
