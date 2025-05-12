package org.parking.backendamparking.DTO;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.parking.backendamparking.Entity.PArea;

@Getter
@Setter
@NoArgsConstructor
public class PAreaDTOResponse {
    private Long id;
    private int daysAllowedParking;
    private String areaName;
    private String city;
    private int postalCode;

    public PAreaDTOResponse(PArea parea) {
        this.daysAllowedParking = parea.getDaysAllowedParking();
        this.areaName = parea.getAreaName();
        this.city = parea.getCity();
        this.postalCode = parea.getPostalCode();

    }
}
