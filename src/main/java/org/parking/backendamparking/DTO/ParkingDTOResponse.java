package org.parking.backendamparking.DTO;


import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.parking.backendamparking.Entity.PArea;
import org.parking.backendamparking.Entity.Parking;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ParkingDTOResponse {
    private Long id;
    private PArea parea;
    private String plateNumber;
    private String carColor;
    private String carBrand;
    private String carModel;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long userId;
    private String userName;

    public ParkingDTOResponse(Parking parking) {
        this.id = parking.getId();
        this.parea = parking.getParea();
        this.plateNumber = parking.getPlateNumber();
        this.carColor = parking.getCarColor();
        this.carBrand = parking.getCarBrand();
        this.carModel = parking.getCarModel();
        this.startTime = parking.getStartTime();
        this.endTime = parking.getEndTime();
        this.userId = parking.getUser() != null ? parking.getUser().getId() : null;
        this.userName = parking.getUser() != null ? parking.getUser().getFirstName() : null;
    }
}

