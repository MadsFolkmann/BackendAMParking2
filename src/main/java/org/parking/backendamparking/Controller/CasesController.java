package org.parking.backendamparking.Controller;

import org.parking.backendamparking.DTO.CasesDTOResponse;
import org.parking.backendamparking.Service.CasesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cases")
@CrossOrigin
public class CasesController {

    private final CasesService casesService;

    public CasesController(CasesService casesService) {
        this.casesService = casesService;
    }

    @GetMapping
    public List<CasesDTOResponse> getAllCases() {
        return casesService.getAllCases();
    }

    @GetMapping("/{id}")
    public CasesDTOResponse getCasesById(@PathVariable Long id) {
        return casesService.getCasesById(id);
    }

    

    @PostMapping
    public CasesDTOResponse addCase(@RequestBody CasesDTOResponse request) {
        return casesService.addCase(request);
    }

    @PutMapping("/{id}")
    public CasesDTOResponse updateCase(@PathVariable Long id, @RequestBody CasesDTOResponse request) {
        return casesService.updateCase(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteCase(@PathVariable Long id) {
        casesService.deleteCase(id);
    }

}
