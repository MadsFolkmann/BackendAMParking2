package org.parking.backendamparking.IntegrationsTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parking.backendamparking.DTO.CaseDTORequest;
import org.parking.backendamparking.Entity.Case;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.CaseRepository;
import org.parking.backendamparking.Repository.UserRepository;
import org.parking.backendamparking.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CaseIntegrationsTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    private Case testCase1;

    @BeforeEach
    void setUp() {

        caseRepository.deleteAll();
        usersRepository.deleteAll();


        testUser = new User();
        testUser.setFirstName("mads");
        testUser.setLastName("mikkelsen");
        testUser.setEmail("mikkelsen@Test.com");
        testUser.setPhoneNumber(231313131);
        testUser.setRentalUnit(1000000009L);
        testUser.setAddress("mads mikkelsen vej 1");
        testUser.setCity("aarhus");
        testUser.setZipCode(3330);
        testUser.setRole(Roles.USER);
        testUser = usersRepository.save(testUser);

        testCase1 = new Case();
        testCase1.setUser(testUser);
        testCase1.setDescription("Test case description");
        testCase1.setTime(LocalDate.from(LocalDateTime.now()));
        testCase1.setDone(false);
        testCase1.setPlateNumber("AB12345");
        testCase1 = caseRepository.save(testCase1);

        Case testCase2 = new Case();
        testCase2.setUser(testUser);
        testCase2.setDescription("Another test case description");
        testCase2.setTime(LocalDate.from(LocalDateTime.now()));
        testCase2.setDone(true);
        testCase2.setPlateNumber("CD67890");
        caseRepository.save(testCase2);

    }

    /**
     * Integration test for getting all cases
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllCases() throws Exception {
        mockMvc.perform(get("/case"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].description").value("Test case description"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].description").value("Another test case description"));

    }

    /**
     * Integration test for getting a case by ID
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCaseById() throws Exception {
        mockMvc.perform(get("/case/" + testCase1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.description").value("Test case description"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.plateNumber").value("AB12345"));
    }

    /**
     * Integration test for getting cases by user ID
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "USER")
    public void testGetCasesByUserId() throws Exception {
        mockMvc.perform(get("/case/user/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$", org.hamcrest.Matchers.hasSize(2)))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[0].description").value("Test case description"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$[1].description").value("Another test case description"));
    }

    /**
     * Adding a new case
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "USER")
    public void testAddCase() throws Exception {
        CaseDTORequest newCaseRequest = new CaseDTORequest();
        newCaseRequest.setUserId(testUser.getId());
        newCaseRequest.setDescription("New case description");
        newCaseRequest.setTime(LocalDate.from(LocalDateTime.now()));
        newCaseRequest.setDone(false);
        newCaseRequest.setPlateNumber("EF12345");

        mockMvc.perform(post("/case/add")
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                .content(objectMapper.writeValueAsString(newCaseRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.description").value("New case description"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.userId").value(testUser.getId().intValue()))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.time").isNotEmpty())
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.done").value(false))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.plateNumber").value("EF12345"));
    }

    /**
     * Integration test for adding a case with missing fields
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "USER")
    public void testAddCase_MissingFields() throws Exception {
        CaseDTORequest newCaseRequest = new CaseDTORequest();
        newCaseRequest.setUserId(testUser.getId());

        mockMvc.perform(post("/case/add")
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                .content(objectMapper.writeValueAsString(newCaseRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.message").value("Description, time, done, and plateNumber are required fields."));
    }

    /**
     * Integration test for updating a case
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "USER")
    public void testUpdateCase() throws Exception {
        CaseDTORequest updatedCaseRequest = new CaseDTORequest();
        updatedCaseRequest.setDescription("Updated case description");
        updatedCaseRequest.setTime(LocalDate.from(LocalDateTime.now()));
        updatedCaseRequest.setDone(true);
        updatedCaseRequest.setPlateNumber("GH12345");

        mockMvc.perform(put("/case/" + testCase1.getId())
                .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                .content(objectMapper.writeValueAsString(updatedCaseRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.description").value("Updated case description"))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.done").value(true))
                .andExpect(org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath("$.plateNumber").value("GH12345"));
    }


    /**
     * Integration test for deleting a case
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCase() throws Exception {
       Long caseId = testCase1.getId();
        mockMvc.perform(delete("/case/" + caseId)
                .with(csrf()))
                .andExpect(status().isNoContent());

        assertFalse(caseRepository.existsById(caseId));
    }

}
