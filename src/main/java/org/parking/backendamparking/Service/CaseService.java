package org.parking.backendamparking.Service;

import jakarta.persistence.EntityNotFoundException;
import org.parking.backendamparking.DTO.CaseDTORequest;
import org.parking.backendamparking.DTO.CaseDTOResponse;
import org.parking.backendamparking.Entity.Case;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.CaseRepository;
import org.parking.backendamparking.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service

public class CaseService {
    private final CaseRepository caseRepository;
    private final UserRepository userRepository;

    public CaseService(CaseRepository caseRepository, UserRepository userRepository) {
        this.caseRepository = caseRepository;
        this.userRepository = userRepository;
    }

    /* Get All Cases */
    public List<CaseDTOResponse> getAllCases() {
        List<Case> allCases = caseRepository.findAll();
        return allCases.stream()
                .map(CaseDTOResponse::new)
                .collect(Collectors.toList());
    }

    /* Get Cases By Case ID */
    public CaseDTOResponse getCasesById(Long id) {
        Case aCase = caseRepository.findById(id).orElse(null);
        return new CaseDTOResponse(aCase);
    }

    /* Get Cases By user ID */
    /* Get Cases By user ID */
    public List<CaseDTOResponse> getCasesByUserId(long userId) {
        List<Case> cases = caseRepository.findAllByUser_Id(userId);
        return cases.stream()
                .map(CaseDTOResponse::new)
                .collect(Collectors.toList());
    }


    /* Add Case */
    public CaseDTOResponse addCase(CaseDTORequest request) {
        Case newCase = new Case();
        newCase.setTime(request.getTime());
        newCase.setDescription(request.getDescription());
        newCase.setDone(request.getDone());

        // Map userId to User entity
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));
        newCase.setUser(user);

        caseRepository.save(newCase);
        return new CaseDTOResponse(newCase);
    }

    /* Update Case */
    public CaseDTOResponse updateCase(Long id, CaseDTORequest request) {
        Case existingCase = caseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Case not found with id: " + id));

        existingCase.setTime(request.getTime());
        existingCase.setDescription(request.getDescription());
        existingCase.setDone(request.getDone());

        // Map userId to User entity
        if (request.getUserId() != null) {
            User user = userRepository.findById(request.getUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + request.getUserId()));
            existingCase.setUser(user);
        }

        caseRepository.save(existingCase);
        return new CaseDTOResponse(existingCase);
    }

    /* Delete Case */
    public void deleteCase (Long id){
        caseRepository.deleteById(id);
    }
}
