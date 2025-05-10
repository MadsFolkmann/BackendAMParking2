package org.parking.backendamparking.Controller;


import org.parking.backendamparking.DTO.PAreaDTOResponse;
import org.parking.backendamparking.Service.PAreaService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pArea")
@CrossOrigin
public class PAreaController {

    private final PAreaService pAreaService;

    public PAreaController(PAreaService pAreaService) {
        this.pAreaService = pAreaService;
    }

    @GetMapping
    public List<PAreaDTOResponse> getAllPAreas() {
        return pAreaService.getAllPAreas();
    }

    @GetMapping("/{areaName}")
    public PAreaDTOResponse getPAreaByAreaName(String areaName) {
        return pAreaService.getPAreaByAreaName(areaName);
    }

    @GetMapping("/{id}")
    public PAreaDTOResponse getPAreaById(Long id) {
        return pAreaService.getPAreaById(id);
    }

    @PostMapping
    public PAreaDTOResponse addPArea(@RequestBody PAreaDTOResponse request) {
        return pAreaService.addPArea(request);
    }

    @PutMapping("/{id}")
    public PAreaDTOResponse updatePArea(@PathVariable Long id, @RequestBody PAreaDTOResponse request) {
        return pAreaService.updatePArea(id, request);
    }

    @DeleteMapping("/{id}")
    public void deletePArea(@PathVariable Long id) {
        pAreaService.deletePArea(id);
    }

}
