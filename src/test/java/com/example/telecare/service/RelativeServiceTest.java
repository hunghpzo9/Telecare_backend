package com.example.telecare.service;

import com.example.telecare.model.Relative;
import com.example.telecare.repository.RelativeRepository;
import com.example.telecare.service.impl.RelativeServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RelativeServiceTest {

    @Mock
    private RelativeRepository relativeRepository;

    @InjectMocks
    private RelativeServiceImpl relativeService;

    private Relative relative;

    @BeforeEach
    void setUp() throws ParseException {
        relative = Relative.builder()
                .id(1)
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
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void givenRelativesList_whenFindAllRelativeByPatientId_thenReturnRelativesList() throws ParseException {
        Relative relative1 = Relative.builder()
                .id(2)
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

        given(relativeRepository.findRelativesByPatientId(1)).willReturn(List.of(relative, relative1));

        List<Relative> relativeList = relativeService.findAllRelativeByPatientId(1);

        assertThat(relativeList).isNotNull();
        assertThat(relativeList.size()).isEqualTo(2);

    }

    @Test
    void givenEmptyRelativesList_whenFindAllRelativeByPatientId_thenReturnEmptyRelativesList() throws ParseException {
        Relative relative1 = Relative.builder()
                .id(1)
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

        given(relativeRepository.findRelativesByPatientId(2)).willReturn(Collections.emptyList());

        List<Relative> relativeList = relativeService.findAllRelativeByPatientId(2);

        assertThat(relativeList).isEmpty();
        assertThat(relativeList.size()).isEqualTo(0);

    }

    @Test
    void givenRelativeObject_whenAddRelative_thenReturnRelativeObject() {
        // given - precondition or setup
        given(relativeRepository.save(relative)).willReturn(relative);

        System.out.println(relativeRepository);
        System.out.println(relativeService);

        // when -  action or the behaviour that we are going test
        Relative savedRelative = relativeService.addRelative(relative);

        System.out.println(savedRelative);
        // then - verify the output
        assertThat(savedRelative).isNotNull();
    }


    @Test
    void givenRelativeId_whenFindRelativeById_thenReturnRelativeObject() {
        // given
        given(relativeRepository.findRelativesByID(1)).willReturn(relative);

        // when
        Relative savedRelative = relativeService.findRelativeById(relative.getId());

        // then
        assertThat(savedRelative).isNotNull();
    }

    @Test
    void givenRelativeObject_whenUpdateRelativeById_thenReturnUpdatedRelative() {
        // given - precondition or setup
        Relative updatedRelative = new Relative();
        updatedRelative.setEmail("ramSay@gmail.com");
        updatedRelative.setFullName("Say Hi");

        // then - verify the output
        assertThat(updatedRelative.getEmail()).isEqualTo("ramSay@gmail.com");
        assertThat(updatedRelative.getFullName()).isEqualTo("Say Hi");

    }
}