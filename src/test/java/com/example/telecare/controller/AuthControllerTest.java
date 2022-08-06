package com.example.telecare.controller;

import com.example.telecare.model.*;
import com.example.telecare.repository.*;
import com.example.telecare.security.PasswordHashService;
import com.example.telecare.service.impl.AuthServiceImpl;
import com.example.telecare.service.impl.TwilioServiceImpl;
import com.example.telecare.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
class AuthControllerTest {
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = objectMapper.writer();

    @Mock
    private UserRepository userRepository;
    @Mock
    private AddressRepository addressRepository;

    @Mock
    private SpecialtyRepository specialtyRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PasswordHashService passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private TwilioServiceImpl twilioService;

    @Mock
    private AuthServiceImpl authService;

    @InjectMocks
    private AuthController authController;

    User successPatient = new User(new HashSet<>(),1,
                        "Nguyễn Thái Hưng",
            new SimpleDateFormat("yyyy-mm-dd").parse("1988-12-20"),
            (byte)0,
            "0906166525",
            "hung.nt231@gmail.com",
            "test123123","asdasdasdasd",
            (byte)0,
            "https://firebasestorage.googleapis.com/v0/b/telecare-chat-storing.appspot.com/o/avatars%2F52fc29b0-ea30-11ec-9544-9fb644460543.jpeg?alt=media&token=56ed8122-af70-459f-8335-cd645bbfda94",
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-06-08 18:12:54"),
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-07-01 17:20:35"),new Patient(),new Doctor(),new Address(),"");

    User duplicatePhonePatient = new User(new HashSet<>(),1,
            "Nguyễn Thái Hưng",
            new SimpleDateFormat("yyyy-mm-dd").parse("1988-12-20"),
            (byte)0,
            "0906166559",
            "hung.nt231@gmail.com",
            "test123123","asdasdasdasd",
            (byte)0,
            "https://firebasestorage.googleapis.com/v0/b/telecare-chat-storing.appspot.com/o/avatars%2F52fc29b0-ea30-11ec-9544-9fb644460543.jpeg?alt=media&token=56ed8122-af70-459f-8335-cd645bbfda94",
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-06-08 18:12:54"),
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-07-01 17:20:35"),new Patient(),new Doctor(),new Address(),"");

    User duplicateEmailPatient = new User(new HashSet<>(),1,
            "Nguyễn Thái Hưng",
            new SimpleDateFormat("yyyy-mm-dd").parse("1988-12-20"),
            (byte)0,
            "0906166525",
            "hung.nt20@gmail.com",
            "test123123","asdasdasdasd",
            (byte)0,
            "https://firebasestorage.googleapis.com/v0/b/telecare-chat-storing.appspot.com/o/avatars%2F52fc29b0-ea30-11ec-9544-9fb644460543.jpeg?alt=media&token=56ed8122-af70-459f-8335-cd645bbfda94",
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-06-08 18:12:54"),
            new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("2022-07-01 17:20:35"),new Patient(),new Doctor(),new Address(),"");


    AuthControllerTest() throws ParseException {
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(authController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    private void registerPatientSuccess() throws Exception {

        Mockito.when(userRepository.save(successPatient)).thenReturn(successPatient);
        Mockito.when(userService.registerPatient(successPatient)).thenReturn(successPatient);

        String content = objectWriter.writeValueAsString(successPatient);

        MockHttpServletRequestBuilder mockRequest =  MockMvcRequestBuilders
                .post("/api/v1/auth/register/patient")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(content);

        mockMvc.perform(mockRequest)
                .andDo(print())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.fullName", Matchers.is("Nguyễn Thái Hưng")))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
    }

}