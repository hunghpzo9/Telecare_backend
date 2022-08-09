package com.example.telecare.controller;

import com.example.telecare.model.Ethnic;
import com.example.telecare.model.Relative;
import com.example.telecare.repository.EthnicRepository;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.service.impl.AuthServiceImpl;
import com.example.telecare.service.impl.EthnicServiceImpl;
import com.example.telecare.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
class EthnicControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private EthnicRepository ethnicRepository;

    @InjectMocks
    private EthnicServiceImpl ethnicService;

    @InjectMocks
    private EthnicController ethnicController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(ethnicController).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void getAllEthnic() {

        List<Ethnic> ethnicList = ethnicRepository.findAll();
        Assertions.assertThat(ethnicList.size()).isEqualTo(54);

    }
}