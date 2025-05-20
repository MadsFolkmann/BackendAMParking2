package org.parking.backendamparking.Controller;

import org.parking.backendamparking.Service.RentalUnitService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/rentalUnit")
public class RentalUnitController {

    private final RentalUnitService rentalUnitService;

    public RentalUnitController(RentalUnitService rentalUnitService) {
        this.rentalUnitService = rentalUnitService;
    }

    //Checks if the RentalUnitController exists

    @GetMapping("/check/{id}")
    public boolean checkRentalUnit(@PathVariable long id) {
        return rentalUnitService.checkRentalUnit(id);
    }
}
