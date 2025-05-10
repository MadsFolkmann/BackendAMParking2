package org.parking.backendamparking.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.parking.backendamparking.Entity.Parking;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class ParkingDTOResponse {
    private Long id;
    private String parkingArea;
    private String plateNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long userId;
    private String userName;

    public ParkingDTOResponse(Parking parking) {
        this.id = parking.getId();
        this.parkingArea = parking.getParkingArea();
        this.plateNumber = parking.getPlateNumber();
        this.startTime = parking.getStartTime();
        this.endTime = parking.getEndTime();
        this.userId = parking.getUser() != null ? parking.getUser().getId() : null;
        this.userName = parking.getUser() != null ? parking.getUser().getFirstName() : null;
    }
}

