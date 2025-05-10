package org.parking.backendamparking.DTO;


import lombok.Getter;
import lombok.Setter;
import org.parking.backendamparking.Entity.PArea;

@Getter
@Setter
public class PAreaDTORequest {
    private int daysAllowedParking;
    private String areaName;
    private String city;
    private String postalCode;

    public PAreaDTORequest() {
    }

    public PAreaDTORequest(PArea parea) {
        this.daysAllowedParking = parea.getDaysAllowedParking();
        this.areaName = parea.getAreaName();
        this.city = parea.getCity();
        this.postalCode = parea.getPostalCode();

    }
}
