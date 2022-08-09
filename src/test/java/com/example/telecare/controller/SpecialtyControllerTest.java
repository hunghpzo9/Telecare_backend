package com.example.telecare.controller;

import com.example.telecare.model.Specialty;
import com.example.telecare.repository.SpecialtyRepository;
import com.example.telecare.service.impl.SpecialtyServiceImp;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class SpecialtyControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private SpecialtyRepository specialtyRepository;

    @Mock
    private SpecialtyServiceImp specialtyService;

    @InjectMocks
    private SpecialtyController specialtyController;

    Specialty specialty1 = new Specialty(1, "Răng - Hàm - Mặt");
    Specialty specialty2 = new Specialty(2, "Nội hô hấp");
    Specialty specialty3 = new Specialty(3, "Thẩm mĩ");


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(specialtyController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllSpecialtyApiNormalCase() throws Exception {
        Mockito.when(specialtyRepository.findAll()).thenReturn(Arrays.asList(specialty1, specialty2,specialty3));
        Mockito.when(specialtyService.findAllSpecialty()).thenReturn(Arrays.asList(specialty1, specialty2,specialty3));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/specialty")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", Matchers.is("Nội hô hấp")))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }

    @Test
    void removeSpecialtyApiNormalCase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/specialty/removeSpecialty?doctorId=1&specialtyId=1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}