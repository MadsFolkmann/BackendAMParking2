package org.parking.backendamparking.IntegrationsTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parking.backendamparking.DTO.PAreaDTORequest;
import org.parking.backendamparking.Entity.PArea;
import org.parking.backendamparking.Repository.PAreaRepository;
import org.parking.backendamparking.Repository.ParkingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class PAreaIntegrationsTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PAreaRepository pAreaRepository;

    @Autowired
    private ParkingRepository parkingRepository;


    @Autowired
    private ObjectMapper objectMapper;

   private PArea testArea1;

    private PArea testArea2;


    @BeforeEach
    void setUp() throws Exception {

        parkingRepository.deleteAll();
        pAreaRepository.deleteAll();

         testArea1 = new PArea();
            testArea1.setDaysAllowedParking(7);
            testArea1.setAreaName("Test Area");
            testArea1.setCity("Test City");
            testArea1.setPostalCode(12345);
            pAreaRepository.save(testArea1);


        testArea2 = new PArea();
            testArea2.setDaysAllowedParking(5);
            testArea2.setAreaName("Test Area 2");
            testArea2.setCity("Test City 2");
            testArea2.setPostalCode(54321);
            pAreaRepository.save(testArea2);
   }


    /**
     * Test to get all P-Areas
     * @throws Exception
     */

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testGetAllPAreas() throws Exception {
        mockMvc.perform(get("/pArea"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].areaName").value("Test Area"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].areaName").value("Test Area 2"));
    }



    /**
     * Test to add a new P-Area
     * @throws Exception
     */
    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testAddPArea() throws Exception {
        PArea newArea = new PArea();
        newArea.setDaysAllowedParking(3);
        newArea.setAreaName("New Area");
        newArea.setCity("New City");
        newArea.setPostalCode(67890);

        mockMvc.perform(post("/pArea/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newArea)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.areaName").value("New Area"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.daysAllowedParking").value(3))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.city").value("New City"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.postalCode").value(67890));
    }

    /**
     * Test to update an existing P-Area
     * @throws Exception
     */

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testUpdatePArea() throws Exception {
        PAreaDTORequest updatedArea = new PAreaDTORequest ();
        updatedArea.setDaysAllowedParking(10);
        updatedArea.setAreaName("Updated Area");
        updatedArea.setCity("Updated City");
        updatedArea.setPostalCode(11111);

        mockMvc.perform(put("/pArea/" + testArea1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                        .content(objectMapper.writeValueAsString(updatedArea)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.areaName").value("Updated Area"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.daysAllowedParking").value(10))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.city").value("Updated City"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.postalCode").value(11111));
    }

    /**
     * Test to delete a P-Area
     * @throws Exception
     */

    @Test
    @WithMockUser(authorities = "ADMIN")
    public void testDeletePArea() throws Exception {
        Long areaId = testArea1.getId();
        mockMvc.perform(delete("/pArea/" + areaId)
                .with(csrf()))
                .andExpect(status().isNoContent());

        assertFalse(pAreaRepository.existsById(areaId));

    }

}
