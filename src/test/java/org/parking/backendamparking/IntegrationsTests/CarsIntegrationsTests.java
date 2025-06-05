package org.parking.backendamparking.IntegrationsTests;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parking.backendamparking.DTO.CarsDTORequest;
import org.parking.backendamparking.Entity.Cars;
import org.parking.backendamparking.Entity.User;
import org.parking.backendamparking.Repository.CarsRepository;
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



import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class CarsIntegrationsTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarsRepository carsRepository;

    @Autowired
private CaseRepository caseRepository;

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private Cars testCar1;
    private Cars testCar2;

    @BeforeEach
    void setUp() {
        caseRepository.deleteAll();
        carsRepository.deleteAll();
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

        testCar1 = new Cars();
        testCar1.setRegistrationNumber("ABC123");
        testCar1.setMake("Toyota");
        testCar1.setModel("Corolla");
        testCar1.setModelYear(2020);
        testCar1.setColor("Red");
        testCar1.setType("Sedan");
        testCar1.setTotalWeight(12000);
        testCar1.setUser(testUser);
        testCar1 = carsRepository.save(testCar1);

        testCar2 = new Cars();
        testCar2.setRegistrationNumber("XYZ456");
        testCar2.setMake("Honda");
        testCar2.setModel("Civic");
        testCar2.setModelYear(2021);
        testCar2.setColor("Blue");
        testCar2.setType("Hatchback");
        testCar2.setTotalWeight(13000);
        testCar2.setUser(testUser);
        testCar2 = carsRepository.save(testCar2);
    }


    /**
     * Integration test for getting all cars
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllCars_Integration() throws Exception {
        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].registrationNumber", is("ABC123")))
                .andExpect(jsonPath("$[1].registrationNumber", is("XYZ456")));

    }

    /**
     * Integration test for getting cars by user ID
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCarsByUserId_Integration() throws Exception {
        mockMvc.perform(get("/cars/user/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].registrationNumber", is("ABC123")))
                .andExpect(jsonPath("$[1].registrationNumber", is("XYZ456")));
    }


    /**
     * Integration test for getting a car by ID
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCarById_Integration() throws Exception {
        mockMvc.perform(get("/cars/" + testCar1.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.registrationNumber", is("ABC123")))
                .andExpect(jsonPath("$.make", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Corolla")))
                .andExpect(jsonPath("$.modelYear", is(2020)))
                .andExpect(jsonPath("$.color", is("Red")))
                .andExpect(jsonPath("$.type", is("Sedan")))
                .andExpect(jsonPath("$.totalWeight", is(12000)));
    }

    /**
     * Integration test for getting a car by ID that does not exist
     * @throws Exception
     */
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCarById_NotFound_Integration() throws Exception {
        mockMvc.perform(get("/cars/9999"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Car not found with id: 9999")));
    }

    // adding car
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddCar_Integration() throws Exception {
        CarsDTORequest newCarRequest = new CarsDTORequest();
        newCarRequest.setRegistrationNumber("LMN789");
        newCarRequest.setMake("Ford");
        newCarRequest.setModel("Focus");
        newCarRequest.setModelYear(2022);
        newCarRequest.setColor("Green");
        newCarRequest.setType("Sedan");
        newCarRequest.setTotalWeight(14000);
        newCarRequest.setUserId(testUser.getId());

        mockMvc.perform(post("/cars/add")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCarRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.registrationNumber", is("LMN789")))
                .andExpect(jsonPath("$.make", is("Ford")))
                .andExpect(jsonPath("$.model", is("Focus")))
                .andExpect(jsonPath("$.modelYear", is(2022)))
                .andExpect(jsonPath("$.color", is("Green")))
                .andExpect(jsonPath("$.type", is("Sedan")))
                .andExpect(jsonPath("$.totalWeight", is(14000)))
                .andExpect(jsonPath("$.userId", is(testUser.getId().intValue())));

    }

    // update car
    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCar_Integration() throws Exception {
        CarsDTORequest updateCarRequest = new CarsDTORequest();
        updateCarRequest.setId(testCar1.getId());
        updateCarRequest.setRegistrationNumber("ABC123");
        updateCarRequest.setMake("Toyota");
        updateCarRequest.setModel("Corolla");
        updateCarRequest.setModelYear(2021);
        updateCarRequest.setColor("Red");
        updateCarRequest.setType("Sedan");
        updateCarRequest.setTotalWeight(12000);
        updateCarRequest.setUserId(testUser.getId());

        mockMvc.perform(put("/cars/" + testCar1.getId())
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateCarRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.registrationNumber", is("ABC123")))
                .andExpect(jsonPath("$.make", is("Toyota")))
                .andExpect(jsonPath("$.model", is("Corolla")))
                .andExpect(jsonPath("$.modelYear", is(2021)))
                .andExpect(jsonPath("$.color", is("Red")))
                .andExpect(jsonPath("$.type", is("Sedan")))
                .andExpect(jsonPath("$.totalWeight", is(12000)))
                .andExpect(jsonPath("$.userId", is(testUser.getId().intValue())));
    }

    /**
     * Delete car by ID integration test
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCar_Integration() throws Exception {
        mockMvc.perform(delete("/cars/" + testCar1.getId())
                        .with(csrf()))
                .andExpect(status().isNoContent());

        // Verify that the car is deleted
        mockMvc.perform(get("/cars/" + testCar1.getId()))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message", is("Car not found with id: " + testCar1.getId())));

    }






}