package com.example.telecare.controller;

import com.example.telecare.dto.interfaces.PrescriptionDTOInf;
import com.example.telecare.model.Prescription;
import com.example.telecare.repository.PrescriptionRepository;
import com.example.telecare.service.impl.PrescriptionServiceImpl;
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class PrescriptionControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private PrescriptionServiceImpl prescriptionService;

    @InjectMocks
    private PrescriptionController prescriptionController;

    PrescriptionDTOInf pre1 = new PrescriptionDTOInf() {
        @Override
        public Integer getId() {
            return 1;
        }

        @Override
        public String getPrescriptionDiagnosis() {
            return "Đau tim";
        }

        @Override
        public String getDoctorName() {
            return "Bác sĩ Nam";
        }

        @Override
        public Date getCreatedAt() {
            try {
                return new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-07-22 16:47:36");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public String getUrl() {
            return "https://firebasestorage.googleapis.com/v0/b/telecare-chat-storing.appspot.com/o/prescriptions%2F1%2FPS_0906166559_27-07-2022%2005%3A32%3A28.pdf?alt=media&token=ae55958f-ebde-40c0-83f2-3d51af59b843";
        }
    };

    PrescriptionDTOInf pre2 = new PrescriptionDTOInf() {
        @Override
        public Integer getId() {
            return 2;
        }

        @Override
        public String getPrescriptionDiagnosis() {
            return "Đau thận";
        }

        @Override
        public String getDoctorName() {
            return "Bác sĩ Hoàng";
        }

        @Override
        public Date getCreatedAt() {
            try {
                return new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-07-21 16:47:36");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public String getUrl() {
            return "https://firebasestorage.googleapis.com/v0/b/telecare-chat-storing.appspot.com/o/prescriptions%2F1%2FPS_0906166559_27-07-2022%2005%3A32%3A28.pdf?alt=media&token=ae55958f-ebde-40c0-83f2-3d51af59b843";
        }
    };

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(prescriptionController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findPrescriptionByPatientIdApiNullCase() throws Exception {
        Mockito.when(prescriptionRepository.getAllPrescription(2, 1)).thenReturn(null);
        Mockito.when(prescriptionService.listAllPrescriptionByPatientId(2, 1)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/prescription/getAll?patientId=2&page=1")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }

    @Test
    void findPrescriptionByPatientIdApiNormalCase() throws Exception {
        Mockito.when(prescriptionRepository.getAllPrescription(1, 1)).thenReturn(Arrays.asList(pre1, pre2));
        Mockito.when(prescriptionService.listAllPrescriptionByPatientId(1, 1)).thenReturn(Arrays.asList(pre1, pre2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/prescription/getAll?patientId=1&page=1")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].prescriptionDiagnosis", Matchers.is("Đau thận")))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }

    @Test
    void getSharedPrescriptionByAppointment() {
    }


    @Test
    void addPrescriptionApiSuccessCase() throws Exception {

        Prescription newPrescription = Prescription
                .builder()
                .id(1)
                .appointmentId(1)
                .diagnosis("Đau tim")
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .guardian(null)
                .note("Khoong")
                .url(null)
                .trace("TCAREbqh0s59")
                .build();

                Mockito.when(prescriptionRepository.save(newPrescription)).thenReturn(newPrescription);
                Mockito.when(prescriptionService.addPrescription(newPrescription)).thenReturn(newPrescription);

                String content = objectWriter.writeValueAsString(newPrescription);

                 MockHttpServletRequestBuilder mockRequest =  MockMvcRequestBuilders
                .post("/api/v1/prescription/addPrescription")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.diagnosis", Matchers.is("Đau tim")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }
}