package org.parking.backendamparking.UnitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.parking.backendamparking.Controller.CarsController;
import org.parking.backendamparking.DTO.CarsDTOResponse;
import org.parking.backendamparking.Service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(CarsController.class)
public class CarsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarsService carsService;

    private List<CarsDTOResponse> carResponses;

    @BeforeEach
    void setUp() {

        CarsDTOResponse car1 = new CarsDTOResponse();
        car1.setId(1L);
        car1.setRegistrationNumber("ABC123");
        car1.setMake("Toyota");
        car1.setModel("Corolla");
        car1.setModelYear(2020);
        car1.setColor("Red");
        car1.setType("Sedan");
        car1.setTotalWeight(12000);

        CarsDTOResponse car2 = new CarsDTOResponse();
        car2.setId(2L);
        car2.setRegistrationNumber("XYZ456");
        car2.setMake("Honda");
        car2.setModel("Civic");
        car2.setModelYear(2021);
        car2.setColor("Blue");
        car2.setType("Hatchback");
        car2.setTotalWeight(13000);

        carResponses = Arrays.asList(car1, car2);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllCars() throws Exception {
        when(carsService.getAllCars()).thenReturn(carResponses);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].registrationNumber", is("ABC123")))
                .andExpect(jsonPath("$[1].registrationNumber", is("XYZ456")));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCarByPlateNumber() throws Exception {
        String plateNumber = "ABC123";
        when(carsService.getCarByPlateNumber(plateNumber)).thenReturn(carResponses.get(0));

        mockMvc.perform(get("/cars/" + plateNumber))
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
     * Test for getting cars by user ID.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetCarsByUserId() throws Exception {
        Long userId = 1L;
        when(carsService.getCarsByUserId(userId)).thenReturn(carResponses);

        mockMvc.perform(get("/cars/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].registrationNumber", is("ABC123")))
                .andExpect(jsonPath("$[1].registrationNumber", is("XYZ456")));
    }



    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddCar() throws Exception {

        CarsDTOResponse carResponse = new CarsDTOResponse();
        carResponse.setId(3L);
        carResponse.setRegistrationNumber("LMN789");
        carResponse.setMake("Ford");
        carResponse.setModel("Focus");
        carResponse.setModelYear(2022);
        carResponse.setColor("Green");
        carResponse.setType("Sedan");
        carResponse.setTotalWeight(14000);

        when(carsService.addCar(org.mockito.ArgumentMatchers.any())).thenReturn(carResponse);

        String carJson = """
                
                {
                    "registrationNumber": "LMN789",
                    "make": "Ford",
                    "model": "Focus",
                    "modelYear": 2022,
                    "color": "Green",
                    "type": "Sedan",
                    "totalWeight": 14000
                }
                """;

        mockMvc.perform(post("/cars/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carJson)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.registrationNumber", is("LMN789")))
                .andExpect(jsonPath("$.make", is("Ford")))
                .andExpect(jsonPath("$.model", is("Focus")))
                .andExpect(jsonPath("$.modelYear", is(2022)))
                .andExpect(jsonPath("$.color", is("Green")))
                .andExpect(jsonPath("$.type", is("Sedan")))
                .andExpect(jsonPath("$.totalWeight", is(14000)));

    }

    /**
     * Test for updating a car by ID.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCar() throws Exception {
        Long carId = 1L;
        CarsDTOResponse updatedCar = new CarsDTOResponse();
        updatedCar.setId(carId);
        updatedCar.setRegistrationNumber("ABC123");
        updatedCar.setMake("Toyota");
        updatedCar.setModel("Corolla");
        updatedCar.setModelYear(2020);
        updatedCar.setColor("Red");
        updatedCar.setType("Sedan");
        updatedCar.setTotalWeight(12000);

        when(carsService.updateCar(Mockito.anyLong(), Mockito.any())).thenReturn(updatedCar);
        String carUpdateJson = """
                
                {
                    "registrationNumber": "ABC123",
                    "make": "Toyota",
                    "model": "Corolla",
                    "modelYear": 2020,
                    "color": "Red",
                    "type": "Sedan",
                    "totalWeight": 12000
                }
                """;

        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(carUpdateJson)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.registrationNumber").value("ABC123"))
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Corolla"))
                .andExpect(jsonPath("$.modelYear").value(2020))
                .andExpect(jsonPath("$.color").value("Red"))
                .andExpect(jsonPath("$.type").value("Sedan"))
                .andExpect(jsonPath("$.totalWeight").value(12000));

    }

    /**
     * Test for deleting a car by ID.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCar() throws Exception {
        Long carId = 1L;

        mockMvc.perform(delete("/cars/" + carId)
                        .with(csrf()))
                .andExpect(status().isOk());

        Mockito.verify(carsService, Mockito.times(1)).deleteCar(carId);
    }

}