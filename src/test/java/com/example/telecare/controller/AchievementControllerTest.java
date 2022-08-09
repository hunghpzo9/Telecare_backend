package com.example.telecare.controller;

import com.example.telecare.model.DoctorAchievement;
import com.example.telecare.repository.AchievementRepository;
import com.example.telecare.service.impl.AchievementServiceImpl;
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
class AchievementControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private AchievementRepository achievementRepository;

    @Mock
    private AchievementServiceImpl achievementService;

    @InjectMocks
    private AchievementController achievementController;

    DoctorAchievement achievement1 = new DoctorAchievement(1, "Ngôi sao y học", 1);
    DoctorAchievement achievement2 = new DoctorAchievement(2, "Ngôi sao quốc tế", 1);
    DoctorAchievement achievement3 = new DoctorAchievement(3, "Bác sĩ của mọi người", 1);
    DoctorAchievement achievement4 = new DoctorAchievement(4, "Giải nhất quốc gia", 2);
    DoctorAchievement achievement5 = new DoctorAchievement(5, "Best Doctor", 2);
    DoctorAchievement achievement6 = new DoctorAchievement(6, "Lmao Achievement", 3);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(achievementController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void addAchievementApiNormalCase() throws Exception {
        DoctorAchievement newDoctorAchievement = DoctorAchievement
                .builder()
                .id(7)
                .achievement("Achievement of God")
                .doctorId(1)
                .build();

        Mockito.when(achievementRepository.save(newDoctorAchievement)).thenReturn(newDoctorAchievement);
        Mockito.when(achievementService.addDoctorAchievement(newDoctorAchievement)).thenReturn(newDoctorAchievement);

        String content = objectWriter.writeValueAsString(newDoctorAchievement);

        MockHttpServletRequestBuilder mockRequest =  MockMvcRequestBuilders
                .put("/api/v1/achievement/addAchievement")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.achievement", Matchers.is("Achievement of God")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    void removeDoctorAchievementApiNormalCase() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/v1/achievement/removeAchievement?achievementId=1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}