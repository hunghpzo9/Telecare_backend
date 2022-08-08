package com.example.telecare.controller;

import com.example.telecare.model.Relative;
import com.example.telecare.repository.RelativeRepository;
import com.example.telecare.service.impl.RelativeServiceImpl;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
class RelativeControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private RelativeRepository relativeRepository;

    @Mock
    private RelativeServiceImpl relativeService;

    @InjectMocks
    private RelativeController relativeController;

    Relative relative1 = new Relative(1,
            "Nguyễn Nam",
            new SimpleDateFormat("yyyy-mm-dd").parse("1988-12-20"),
            (byte) 0,
            "Bố",
            "1111111111",
            "bo@gmail.com",
            2,
            "https://static.remove.bg/remove-bg-web/669d7b10b2296142983fac5a5243789bd1838d00/assets/start-1abfb4fe2980eabfbbaaa4365a0692539f7cd2725f324f904565a9a744f8e214.jpg",
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-06-08 18:12:54"),
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-07-01 17:20:35"),
            3);
    Relative relative2 = new Relative(1,
            "Nguyễn Tuấn",
            new SimpleDateFormat("yyyy-mm-dd").parse("1985-14-12"),
            (byte) 0,
            "Bố",
            "2222222222",
            "bo2@gmail.com",
            2,
            "https://static.remove.bg/remove-bg-web/669d7b10b2296142983fac5a5243789bd1838d00/assets/start-1abfb4fe2980eabfbbaaa4365a0692539f7cd2725f324f904565a9a744f8e214.jpg",
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-06-08 18:12:55"),
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-07-01 17:20:36"),
            3);
    Relative relative3 = new Relative(1,
            "Lê Na",
            new SimpleDateFormat("yyyy-mm-dd").parse("1990-01-14"),
            (byte) 1,
            "Mẹ",
            "3333333333",
            "me@gmail.com",
            1,
            "https://static.remove.bg/remove-bg-web/669d7b10b2296142983fac5a5243789bd1838d00/assets/start-1abfb4fe2980eabfbbaaa4365a0692539f7cd2725f324f904565a9a744f8e214.jpg",
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-06-08 18:12:51"),
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-07-01 17:20:31"),
            3);

    RelativeControllerTest() throws ParseException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(relativeController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void getAllRelativeApiTestNormalCase() throws Exception {
        List<Relative> relativeList = new ArrayList<>(Arrays.asList(relative1, relative2));

        Mockito.when(relativeRepository.findRelativesByPatientId(2)).thenReturn(relativeList);
        Mockito.when(relativeService.findAllRelativeByPatientId(2)).thenReturn(relativeList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/relative/patientId=2")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].fullName", Matchers.is("Nguyễn Tuấn")))
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }
    @Test
    public void getAllRelativeApiTestNullCase() throws Exception {

        Mockito.when(relativeRepository.findRelativesByPatientId(3)).thenReturn(null);
        Mockito.when(relativeService.findAllRelativeByPatientId(3)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/relative/patientId=3")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);

    }

    @Test
    public void getRelativeByIdApiTestNormalCase() throws Exception {
        Relative relative = relative1;

        Mockito.when(relativeRepository.findRelativesByID(1)).thenReturn(relative);
        Mockito.when(relativeService.findRelativeById(1)).thenReturn(relative);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/relative/1")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.fullName", Matchers.is("Nguyễn Nam")))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    public void getRelativeByIdApiTestNullCase() throws Exception {
        Mockito.when(relativeRepository.findRelativesByID(0)).thenReturn(null);
        Mockito.when(relativeService.findRelativeById(0)).thenReturn(null);


        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/relative/0")
                        .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", nullValue()))
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

    @Test
    public void addRelativeApiTestSuccessCase() throws Exception{
        Relative newRelative = Relative.builder()
                .id(4)
                .fullName("Nguyễn Huệ")
                .dateOfBirth(new SimpleDateFormat("yyyy-mm-dd").parse("1991-05-13"))
                .gender((byte) 1)
                .relationship("Bà Ngoại")
                .phone("1234569877")
                .email("athea@gmail.com")
                .patientId(1)
                .imageUrl(null)
                .createdAt(new Date())
                .updatedAt(new Date())
                .ethnicId(2)
                .build();

        Mockito.when(relativeRepository.save(newRelative)).thenReturn(newRelative);
        Mockito.when(relativeService.addRelative(newRelative)).thenReturn(newRelative);

        String content = objectWriter.writeValueAsString(newRelative);

        MockHttpServletRequestBuilder mockRequest =  MockMvcRequestBuilders
                        .post("/api/v1/relative/addNew")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.fullName", Matchers.is("Nguyễn Huệ")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);


    }

    @Test
    public void updateRelativeApiTestSuccessCase() throws Exception {
        Relative updateRelative = Relative.builder()
                .id(relative1.getId())
                .fullName("Nguyễn Nam Vương")
                .dateOfBirth(new SimpleDateFormat("yyyy-mm-dd").parse("1991-05-14"))
                .gender((byte) 0)
                .relationship("Ông ngoại")
                .email("athea@gmail.com")
                .imageUrl("Có")
                .phone(relative1.getPhone())
                .patientId(relative1.getPatientId())
                .createdAt(relative1.getCreatedAt())
                .updatedAt(new Date())
                .ethnicId(3)
                .build();

        Mockito.when(relativeRepository.findRelativesByID(relative1.getId())).thenReturn((relative1));
        Mockito.when(relativeRepository.save(updateRelative)).thenReturn(updateRelative);

        String updateContent = objectWriter.writeValueAsString(updateRelative);

        MockHttpServletRequestBuilder mockRequest =  MockMvcRequestBuilders
                .put("/api/v1/relative/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(updateContent);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.fullName", Matchers.is("Nguyễn Nam Vương")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }
}