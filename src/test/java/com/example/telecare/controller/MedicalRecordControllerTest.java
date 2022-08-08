package com.example.telecare.controller;

import com.example.telecare.dto.interfaces.MedicalRecordDTOInf;
import com.example.telecare.dto.interfaces.MedicalRecordDetailDTO;
import com.example.telecare.model.MedicalRecord;
import com.example.telecare.repository.MedicalRecordRepository;
import com.example.telecare.service.impl.MedicalRecordServiceImpl;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class MedicalRecordControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    @Mock
    private MedicalRecordServiceImpl medicalRecordService;

    @InjectMocks
    private MedicalRecordController medicalRecordController;


    MedicalRecordDTOInf medi1 = new MedicalRecordDTOInf() {
        @Override
        public Integer getId() {
            return 1;
        }

        @Override
        public String getMedicalRecordName() {
            return "Bệnh án gan";
        }

        @Override
        public String getDoctorName() {
            return "Bác sĩ Nam";
        }

        @Override
        public Date getCreatedAt() {
            try {
                return new SimpleDateFormat("yyyy-mm-dd").parse("1991-05-13");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        public String getReason() {
            return "Đau tim";
        }

        @Override
        public String getMainDisease() {
            return "Suy tim";
        }

        @Override
        public String getUrl() {
            return null;
        }
    };

    MedicalRecordDTOInf medi2 = new MedicalRecordDTOInf() {
        @Override
        public Integer getId() {
            return 2;
        }

        @Override
        public String getMedicalRecordName() {
            return "Bệnh án thận";
        }

        @Override
        public String getDoctorName() {
            return "Bác sĩ Nam";
        }

        @Override
        public Date getCreatedAt() {
            try {
                return new SimpleDateFormat("yyyy-mm-dd").parse("1991-05-13");
            } catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        public String getReason() {
            return "Đau thận";
        }

        @Override
        public String getMainDisease() {
            return "Suy thận";
        }

        @Override
        public String getUrl() {
            return null;
        }
    };
    MedicalRecordDetailDTO details = new MedicalRecordDetailDTO() {
        @Override
        public Integer getId() {
            return 1;
        }

        @Override
        public String getMedicalRecordName() {
            return "Bệnh án Tim";
        }

        @Override
        public String getReason() {
            return "Đau tim";
        }

        @Override
        public String getMainDisease() {
            return "A";
        }

        @Override
        public String getUrl() {
            return null;
        }

        @Override
        public Integer getType() {
            return 1;
        }

        @Override
        public String getExternal() {
            return "A";
        }

        @Override
        public String getGuardianDetail() {
            return "A";
        }

        @Override
        public String getGuardianPhone() {
            return "a";
        }

        @Override
        public String getMedicalProcess() {
            return "a";
        }

        @Override
        public String getSelfHistory() {
            return "a";
        }

        @Override
        public String getFamilyHistory() {
            return "a";
        }

        @Override
        public String getBodyExamination() {
            return "a";
        }

        @Override
        public String getOrgansExamination() {
            return "a";
        }

        @Override
        public String getSummary() {
            return "a";
        }

        @Override
        public String getIncludeDisease() {
            return "a";
        }

        @Override
        public String getFirstAmount() {
            return "a";
        }

        @Override
        public String getSecondAmount() {
            return "a";
        }

        @Override
        public Integer getIsEdited() {
            return 0;
        }
    };


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(medicalRecordController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllMedicalRecordByPatientIdApiNormalCase() throws Exception {
        Mockito.when(medicalRecordRepository.getMedicalRecordByPatientId(1, 1)).thenReturn(Arrays.asList(medi1,medi2));
        Mockito.when(medicalRecordService.getAllMedicalRecordByPatientId(1, 1)).thenReturn(Arrays.asList(medi1,medi2));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/medicalRecord/getAll?patientId=1&page=1")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].medicalRecordName", Matchers.is("Bệnh án thận")))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void getAllMedicalRecordByPatientIdApiNullCase() throws Exception {
        Mockito.when(medicalRecordRepository.getMedicalRecordByPatientId(2, 1)).thenReturn(null);
        Mockito.when(medicalRecordService.getAllMedicalRecordByPatientId(2, 1)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/medicalRecord/getAll?patientId=2&page=1")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void medicalRecordDTOInfListApiNormalCase() throws Exception {
        Mockito.when(medicalRecordRepository.getMedicalRecordDetailsByAppointmentId(1)).thenReturn(details);
        Mockito.when(medicalRecordService.getMedicalRecordDetailByAppointmentId(1)).thenReturn(details);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/medicalRecord/getMedicalRecordDetail?appointmentId=1")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.medicalRecordName", Matchers.is("Bệnh án Tim")))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }

    @Test
    void medicalRecordDTOInfListApiNullCase() throws Exception {
        Mockito.when(medicalRecordRepository.getMedicalRecordDetailsByAppointmentId(2)).thenReturn(null);
        Mockito.when(medicalRecordService.getMedicalRecordDetailByAppointmentId(2)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/medicalRecord/getMedicalRecordDetail?appointmentId=2")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }

    @Test
    void getShareMedicalRecordList() {
    }

    @Test
    void getSharedPrescriptionByAppointment() {
    }

    @Test
    void addMedicalRecordApiSuccessCase() throws Exception {
        MedicalRecord newMedicalRecord = MedicalRecord
                .builder()
                .id(1)
                .medicalRecordName("Bệnh án tim")
                .appointmentId(2)
                .build();

        Mockito.when(medicalRecordRepository.save(newMedicalRecord)).thenReturn(newMedicalRecord);
        Mockito.when(medicalRecordService.addMedicalRecord(newMedicalRecord, 22)).thenReturn(newMedicalRecord);

        String content = objectWriter.writeValueAsString(newMedicalRecord);

        MockHttpServletRequestBuilder mockRequest =  MockMvcRequestBuilders
                .post("/api/v1/medicalRecord/addMedicalRecord?yearCode=22")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.medicalRecordName", Matchers.is("Bệnh án tim")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }

    @Test
    void updateMedicalRecordApiSuccessCase() throws Exception {
        MedicalRecord updateMedicalRecord = MedicalRecord
                .builder()
                .id(1)
                .medicalRecordName("Bệnh án Thận")
                .appointmentId(2)
                .url("url")
                .build();

        Mockito.when(medicalRecordRepository.save(updateMedicalRecord)).thenReturn(updateMedicalRecord);
        Mockito.when(medicalRecordRepository.findMedicalRecordByAppointmentId(2)).thenReturn(updateMedicalRecord);

        String content = objectWriter.writeValueAsString(updateMedicalRecord);

        MockHttpServletRequestBuilder mockRequest =  MockMvcRequestBuilders
                .put("/api/v1/medicalRecord/updateMedicalRecord?appointmentId=2")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.medicalRecordName", Matchers.is("Bệnh án Thận")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }
}