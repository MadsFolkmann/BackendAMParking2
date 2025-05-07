package org.parking.backendamparking.Service;

import org.parking.backendamparking.DTO.CasesDTOResponse;
import org.parking.backendamparking.Entity.Cases;
import org.parking.backendamparking.Repository.CasesRepository;
import org.parking.backendamparking.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CasesService {
    private final CasesRepository casesRepository;
    private final UserRepository userRepository;

    public CasesService(CasesRepository casesRepository, UserRepository userRepository) {
        this.casesRepository = casesRepository;
        this.userRepository = userRepository;
    }

    /* Get All Cases */
    public List<CasesDTOResponse> getAllCases() {
        List<Cases> allCases = casesRepository.findAll();
        return allCases.stream()
                .map(CasesDTOResponse::new)
                .collect(Collectors.toList());
    }

    /* Get Cases By Case ID */
    public CasesDTOResponse getCasesById(Long id) {
        Cases cases = casesRepository.findById(id).orElse(null);
        return new CasesDTOResponse(cases);
    }



    /* Add Case */
    public CasesDTOResponse addCase(CasesDTOResponse request) {
        Cases newCase = new Cases();
        newCase.setTime(request.getTime());
        newCase.setDescription(request.getDescription());

        casesRepository.save(newCase);
        return new CasesDTOResponse(newCase);
    }

    /* Update Case */
    public CasesDTOResponse updateCase(Long id, CasesDTOResponse request) {
        Cases existingCase = casesRepository.findById(id).orElse(null);
        if (existingCase != null) {
            existingCase.setTime(request.getTime());
            existingCase.setDescription(request.getDescription());
            casesRepository.save(existingCase);
            return new CasesDTOResponse(existingCase);

        } else {
            return null;

        }
    }

    /* Delete Case */
    public void deleteCase (Long id){
        casesRepository.deleteById(id);
    }
}
