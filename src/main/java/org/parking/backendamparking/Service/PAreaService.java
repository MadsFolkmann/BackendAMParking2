package org.parking.backendamparking.Service;

import org.parking.backendamparking.DTO.PAreaDTOResponse;
import org.parking.backendamparking.Entity.PArea;
import org.parking.backendamparking.Repository.PAreaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PAreaService {

    private final PAreaRepository pAreaRepository;

    public PAreaService(PAreaRepository pAreaRepository) {
        this.pAreaRepository = pAreaRepository;
    }

    /** Get All P-Areas
     * @return List of PAreaDTOResponse
     */

    /* Get All P-Areas */
    public List<PAreaDTOResponse> getAllPAreas() {
        List<PArea> allPAreas = pAreaRepository.findAll();
        return allPAreas.stream()
                .map(PAreaDTOResponse::new)
                .collect(Collectors.toList());
    }

    /** Get P-Area By Area Name
     * @param areaName the name of the area to be retrieved
     * @return PAreaDTOResponse
     */

    /* Get P-Area By Area Name */
    public PAreaDTOResponse getPAreaByAreaName(String areaName) {
        PArea pArea = pAreaRepository.findByAreaName(areaName);
        return new PAreaDTOResponse(pArea);
    }

    /** Get P-Area By Id
     * @param id the ID of the P-Area to be retrieved
     * @return PAreaDTOResponse
     */

  /* Get P-Area by Id */
    public PAreaDTOResponse getPAreaById(Long id) {
        PArea pArea = pAreaRepository.findById(id).orElse(null);
        return new PAreaDTOResponse(pArea);
    }



    /** Add P-Area
     * @param request the PAreaDTOResponse containing the details of the area to be added
     * @return PAreaDTOResponse
     */

    /* Add P-Area */
    public PAreaDTOResponse addPArea(PAreaDTOResponse request) {
        PArea newPArea = new PArea();
        newPArea.setAreaName(request.getAreaName());
        newPArea.setCity(request.getCity());
        newPArea.setPostalCode(request.getPostalCode());
        newPArea.setDaysAllowedParking(request.getDaysAllowedParking());

        pAreaRepository.save(newPArea);
        return new PAreaDTOResponse(newPArea);
    }

    /** Update P-Area
     * @param id the ID of the P-Area to be updated
     * @param request the PAreaDTOResponse containing the updated details
     * @return PAreaDTOResponse
     */

    /* Update P-Area */

    public PAreaDTOResponse updatePArea(Long id, PAreaDTOResponse request) {
        PArea existingPArea = pAreaRepository.findById(id).orElse(null);
        if (existingPArea != null) {
            existingPArea.setAreaName(request.getAreaName());
            existingPArea.setCity(request.getCity());
            existingPArea.setPostalCode(request.getPostalCode());
            existingPArea.setDaysAllowedParking(request.getDaysAllowedParking());

            pAreaRepository.save(existingPArea);
            return new PAreaDTOResponse(existingPArea);
        } else {
            return null;
        }
    }

    /** Delete P-Area
     * @param id the ID of the P-Area to be deleted
     */

    /* Delete P-Area */
    public void deletePArea(Long id) {
        pAreaRepository.deleteById(id);
    }


}
