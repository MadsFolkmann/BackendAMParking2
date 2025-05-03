package org.parking.backendamparking.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Parking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pArea;
    private Long plateNumber;
    private String startTime;
    private String endTime;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
