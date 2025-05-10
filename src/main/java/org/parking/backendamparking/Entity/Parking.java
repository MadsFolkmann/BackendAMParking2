package org.parking.backendamparking.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String parkingArea;
    private String plateNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
