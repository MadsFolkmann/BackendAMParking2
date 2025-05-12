package org.parking.backendamparking.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class RentalUnit {
    @Id
    private Long unitNumber;

    public RentalUnit() {}
    public RentalUnit(Long unitNumber) {
        this.unitNumber = unitNumber;
    }
}
