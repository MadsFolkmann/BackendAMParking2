package org.parking.backendamparking.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class PArea {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int DaysAllowedParking;
    private String areaName;
    private String city;
    private String postalCode;

}
