package org.parking.backendamparking.IntegrationsTests;


import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parking.backendamparking.DTO.ParkingDTORequest;
import org.parking.backendamparking.Entity.PArea;
import org.parking.backendamparking.Entity.Parking;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.*;
import org.parking.backendamparking.Roles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class ParkingIntegrationsTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ParkingRepository parkingRepository;

    @Autowired
    private CaseRepository caseRepository;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private PAreaRepository pAreaRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private Parking testParking1;
    private Parking testParking2;
    private PArea testParea1;

    @BeforeEach
    void setUp() {
        caseRepository.deleteAll();
        pAreaRepository.deleteAll();
        usersRepository.deleteAll();
        parkingRepository.deleteAll();

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

        testParea1 = new PArea();
        testParea1.setAreaName("P-område 1");
        testParea1.setCity("Rødovre");
        testParea1.setPostalCode(2610);
        testParea1.setDaysAllowedParking(5);
        testParea1 = pAreaRepository.save(testParea1);




        testParking1 = new Parking();
        testParking1.setPlateNumber("ABC123");
        testParking1.setUser(testUser);
        testParking1.setParea(testParea1);
        testParking1.setStartTime(LocalDateTime.now().minusHours(2));
        testParking1.setEndTime(LocalDateTime.now().plusHours(1));
        testParking1 = parkingRepository.save(testParking1);

        testParking2 = new Parking();
        testParking2.setPlateNumber("XYZ456");
        testParking2.setUser(testUser);
        testParking2.setParea(testParea1);
        testParking2.setStartTime(LocalDateTime.now().minusHours(3));
        testParking2.setEndTime(LocalDateTime.now().minusHours(1));
        testParking2 = parkingRepository.save(testParking2);
    }

    /**
     * Integration test for getting all parkings.
     * @throws Exception if the request fails
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllParkings() throws Exception {
        mockMvc.perform(get("/parking"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].plateNumber", is("ABC123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].plateNumber", is("XYZ456")));
    }

    /**
     * Integration test for getting a parking by ID.
     * @throws Exception if the request fails
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetParkingById() throws Exception {
        mockMvc.perform(get("/parking/" + testParking1.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.plateNumber", is("ABC123")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", is("mads")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", is(testUser.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parea.areaName", is("P-område 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").isNotEmpty());
    }

    /**
     * Integration test for adding a new parking.
     * @throws Exception if the request fails
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddParking() throws Exception {
        ParkingDTORequest newParkingRequest = new ParkingDTORequest();
        newParkingRequest.setPlateNumber("LMN789");
        newParkingRequest.setParea(testParea1);
        newParkingRequest.setStartTime(LocalDateTime.now());
        newParkingRequest.setEndTime(LocalDateTime.now().plusHours(2));
        newParkingRequest.setUserId(testUser.getId());

        mockMvc.perform(post("/parking/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newParkingRequest)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.plateNumber", is("LMN789")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", is(testUser.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", is("mads")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parea.areaName", is("P-område 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").isNotEmpty());
    }

    /**
     * Integration test for updating an existing parking.
     * @throws Exception if the request fails
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateParking() throws Exception {
        ParkingDTORequest updateParkingRequest = new ParkingDTORequest();
        updateParkingRequest.setPlateNumber("XYZ789");
        updateParkingRequest.setParea(testParea1);
        updateParkingRequest.setStartTime(LocalDateTime.now().minusHours(1));
        updateParkingRequest.setEndTime(LocalDateTime.now().plusHours(3));
        updateParkingRequest.setUserId(testUser.getId());

        mockMvc.perform(put("/parking/" + testParking1.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateParkingRequest)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.plateNumber", is("XYZ789")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId", is(testUser.getId().intValue())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userName", is("mads")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.parea.areaName", is("P-område 1")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.startTime").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime").isNotEmpty());
    }

    /**
     * Integration test for deleting a parking by ID.
     * @throws Exception if the request fails
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteParking() throws Exception {
        Long parkingId = testParking1.getId();
        mockMvc.perform(delete("/parking/ " + parkingId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        assertFalse(parkingRepository.existsById(parkingId));
    }
}
