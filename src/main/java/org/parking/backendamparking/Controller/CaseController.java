package org.parking.backendamparking.Controller;

import org.parking.backendamparking.DTO.CaseDTORequest;
import org.parking.backendamparking.DTO.CaseDTOResponse;
import org.parking.backendamparking.Service.CaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/case")
@CrossOrigin
public class CaseController {

    private final CaseService caseService;

    public CaseController(CaseService caseService) {
        this.caseService = caseService;
    }

    @GetMapping
    public List<CaseDTOResponse> getAllCases() {
        return caseService.getAllCases();
    }

    @GetMapping("/{id}")
    public CaseDTOResponse getCasesById(@PathVariable Long id) {
        return caseService.getCasesById(id);
    }

    @GetMapping("/user/{userId}")
    public List<CaseDTOResponse> getCaseByUserId(@PathVariable Long userId) {
        return caseService.getCasesByUserId(userId);
    }

    @PostMapping("/add")
    public ResponseEntity<CaseDTOResponse> addCase(@RequestBody CaseDTORequest request) {
        CaseDTOResponse savedCase = caseService.addCase(request);
        return ResponseEntity.status(201).body(savedCase);
    }

    @PutMapping("/{id}")
    public CaseDTOResponse updateCase(@PathVariable Long id, @RequestBody CaseDTORequest request) {
        return caseService.updateCase(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCase(@PathVariable Long id) {
        caseService.deleteCase(id);
        return ResponseEntity.noContent().build();
    }

}
