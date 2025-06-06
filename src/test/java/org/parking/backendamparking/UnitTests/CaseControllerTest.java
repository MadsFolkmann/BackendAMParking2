package org.parking.backendamparking.UnitTests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.parking.backendamparking.Controller.CaseController;
import org.parking.backendamparking.DTO.CaseDTOResponse;
import org.parking.backendamparking.Service.CaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CaseController.class)
public class CaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CaseService caseService;

    private List<CaseDTOResponse> caseResponses;

    @BeforeEach
    void setUp() {
        CaseDTOResponse case1 = new CaseDTOResponse();
        case1.setId(1L);
        case1.setDescription("Test Case 1");
        case1.setUserId(1L);
        case1.setDone(true);
        case1.setPlateNumber("AB123CD");
        case1.setTime(LocalDate.from(LocalDateTime.parse("2023-10-01T10:00:00")));

        CaseDTOResponse case2 = new CaseDTOResponse();
        case2.setId(2L);
        case2.setDescription("Test Case 2");
        case2.setUserId(2L);
        case2.setDone(false);
        case2.setPlateNumber("XY456YZ");
        case2.setTime(LocalDate.from(LocalDateTime.parse("2023-10-01T10:00:00")));

        caseResponses = Arrays.asList(case1, case2);
    }

    /**
     * Test to get all cases.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testGetAllCases() throws Exception {
        when(caseService.getAllCases()).thenReturn(caseResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/case"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }


    /**
     * Test to get a case by ID.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "USER")
    public void testGetCaseById() throws Exception {
        when(caseService.getCasesById(1L)).thenReturn(caseResponses.get(0));

        mockMvc.perform(MockMvcRequestBuilders.get("/case/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description", Matchers.is("Test Case 1")));
    }

    /**
     * Test to get cases by user ID.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "USER")
    public void testGetCasesByUserId() throws Exception {
        when(caseService.getCasesByUserId(1L)).thenReturn(caseResponses);

        mockMvc.perform(MockMvcRequestBuilders.get("/case/user/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)));
    }

    /**
     * Test to get cases by plate number.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAddCase() throws Exception {
        CaseDTOResponse newCase = new CaseDTOResponse();
        newCase.setId(3L);
        newCase.setDescription("New Test Case");
        newCase.setUserId(1L);
        newCase.setDone(false);
        newCase.setPlateNumber("AB123CD");
        newCase.setTime(LocalDate.from(LocalDateTime.parse("2023-10-01T10:00:00")));

        when(caseService.addCase(Mockito.any())).thenReturn(newCase);

        String caseJson = """
                {
                    "description": "New Test Case",
                    "userId": 1,
                    "done": false,
                    "plateNumber": "AB123CD",
                    "time": "2023-10-01T10:00:00"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/case/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(caseJson)
                        .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("New Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(false))
                .andExpect(MockMvcResultMatchers.jsonPath("$.plateNumber").value("AB123CD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time").value("2023-10-01"));
    }

    /**
     * Test to update a case.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testUpdateCase() throws Exception {
        CaseDTOResponse updatedCase = new CaseDTOResponse();
        updatedCase.setId(1L);
        updatedCase.setDescription("Updated Test Case");
        updatedCase.setUserId(1L);
        updatedCase.setPlateNumber("AB123CD");
        updatedCase.setDone(true);
        updatedCase.setTime(LocalDate.from(LocalDateTime.parse("2023-10-01T10:00:00")));

        when(caseService.updateCase(Mockito.anyLong(), Mockito.any())).thenReturn(updatedCase);

        String caseJson = """
                {
                    "description": "Updated Test Case",
                    "plateNumber": "AB123CD",
                    "userId": 1,
                    "done": true,
                    "time": "2023-10-01T10:00:00"
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.put("/case/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(caseJson)
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Updated Test Case"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.done").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time").value("2023-10-01"));
    }

    /**
     * Test to delete a case.
     * @throws Exception
     */

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testDeleteCase() throws Exception {
        Long caseId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/case/" + caseId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        Mockito.verify(caseService, Mockito.times(1)).deleteCase(caseId);
    }
}