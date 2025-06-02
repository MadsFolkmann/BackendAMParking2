package org.parking.backendamparking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.parking.backendamparking.Controller.CarsController;
import org.parking.backendamparking.DTO.CarsDTOResponse;
import org.parking.backendamparking.Service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
        // Mock setup flyttet ind i test metoden
        when(carsService.getAllCars()).thenReturn(carResponses);

        mockMvc.perform(get("/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpected(jsonPath("$[0].registrationNumber", is("ABC123")))
                .andExpected(jsonPath("$[1].registrationNumber", is("XYZ456")));
    }
}