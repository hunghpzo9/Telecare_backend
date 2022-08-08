package com.example.telecare.controller;

import com.example.telecare.model.DoctorExperience;
import com.example.telecare.repository.ExperienceRepository;
import com.example.telecare.service.impl.ExperienceServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class ExperienceControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private ExperienceRepository experienceRepository;

    @Mock
    private ExperienceServiceImpl experienceService;

    @InjectMocks
    private ExperienceController experienceController;

    DoctorExperience exp1 = new DoctorExperience(1, "2 năm", 1);
    DoctorExperience exp2 = new DoctorExperience(2, "3 năm", 1);
    DoctorExperience exp3 = new DoctorExperience(3, "4 năm", 1);
    DoctorExperience exp4 = new DoctorExperience(4, "5 năm", 2);
    DoctorExperience exp5 = new DoctorExperience(5, "6 năm", 3);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(experienceController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addDoctorExperienceApiNormalCase() throws Exception {
        DoctorExperience newDoctorExperience = DoctorExperience
                .builder()
                .id(6)
                .experience("10 Năm")
                .doctorId(1)
                .build();

        Mockito.when(experienceRepository.save(newDoctorExperience)).thenReturn(newDoctorExperience);
        Mockito.when(experienceService.addDoctorExperience(newDoctorExperience)).thenReturn(newDoctorExperience);

        String content = objectWriter.writeValueAsString(newDoctorExperience);

        MockHttpServletRequestBuilder mockRequest =  MockMvcRequestBuilders
                .put("/api/v1/experience/addExp")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.experience", Matchers.is("10 Năm")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void removeDoctorExperienceApiNormalCase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/experience/removeExp?experienceId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}