package com.example.telecare.controller;

import com.example.telecare.dto.interfaces.PatientDTOInf;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.repository.PatientRepository;
import com.example.telecare.security.PasswordHashService;
import com.example.telecare.service.impl.PatientServiceImpl;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class PatientControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PatientServiceImpl patientService;


    @InjectMocks
    private PatientServiceImpl patientServiceInject;

    @InjectMocks
    private PatientController patientController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(patientController).build();
    }
    PatientDTOInf patientDTOInf = new PatientDTOInf() {
        @Override
        public Integer getId() {
            return null;
        }

        @Override
        public String getFullName() {
            return "Nguyen Thai Hung";
        }

        @Override
        public Date getDob() {
            return null;
        }

        @Override
        public String getEmail() {
            return null;
        }

        @Override
        public Byte getGender() {
            return null;
        }

        @Override
        public String getPhone() {
            return null;
        }

        @Override
        public String getImageUrl() {
            return null;
        }

        @Override
        public String getStreetName() {
            return null;
        }

        @Override
        public Integer getEthnicId() {
            return null;
        }

        @Override
        public String getBloodType() {
            return null;
        }

        @Override
        public Double getHeight() {
            return null;
        }

        @Override
        public Double getWeight() {
            return null;
        }

        @Override
        public String getJob() {
            return null;
        }

        @Override
        public String getJobPlace() {
            return null;
        }

        @Override
        public Integer getAddressId() {
            return null;
        }

        @Override
        public String getCityId() {
            return null;
        }

        @Override
        public String getDistrictId() {
            return null;
        }

        @Override
        public String getWardId() {
            return null;
        }

        @Override
        public Byte getIsActive() {
            return null;
        }

        @Override
        public String getReason() {
            return null;
        }

        @Override
        public String getInsurance() {
            return null;
        }
    };
    @AfterEach
    void tearDown() {
    }
    @Test
    public void getPatientByIdSuccess() throws Exception {
        Mockito.when(patientRepository.findPatientById(1)).thenReturn(patientDTOInf);
        Mockito.when(patientService.findPatientById(1)).thenReturn(patientDTOInf);
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/patient/1")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.fullName", Matchers.is("Nguyen Thai Hung")))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }

    @Test
    public void getPatientByIdNotFound() {
        var exception = assertThrows(NotFoundException.class, () ->
                patientServiceInject.findPatientById(4)
        );
        assertEquals("Bệnh nhân không tồn tại", exception.getMessage());

    }
}