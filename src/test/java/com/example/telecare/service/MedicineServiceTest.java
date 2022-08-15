package com.example.telecare.service;

import com.example.telecare.dto.interfaces.MedicineNameDTO;
import com.example.telecare.repository.MedicineRepository;
import com.example.telecare.service.impl.MedicineServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class MedicineServiceTest {

    @Mock
    private MedicineRepository medicineRepository;

    @InjectMocks
    private MedicineServiceImpl medicineService;

    MedicineNameDTO medicine1;
    MedicineNameDTO medicine2;
    MedicineNameDTO medicine3;

    @BeforeEach
    void setUp() {
        medicine1 = new MedicineNameDTO() {
            @Override
            public String getName() {
                return "Thuoc A";
            }
        };
        medicine2 = new MedicineNameDTO() {
            @Override
            public String getName() {
                return "Thuoc B1";
            }
        };
        medicine3 = new MedicineNameDTO() {
            @Override
            public String getName() {
                return "Me C";
            }
        };
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void giveSearchText_whenFindAllMedicineByNameDistinct_thenReturnListMedicineName() {
        given(medicineRepository.getAllMedicineNameDistinct("Thu", 0)).willReturn(List.of(medicine1, medicine2));

        List<MedicineNameDTO> medicineNameDTOList = medicineService.getAllMedicineNameDistinct("Thu", 0);

        assertThat(medicineNameDTOList).isNotNull();
        assertThat(medicineNameDTOList.size()).isEqualTo(2);
    }

    @Test
    void giveSearchText_whenFindAllMedicineByNameDistinct_thenReturnEmptyListMedicineName() {
        given(medicineRepository.getAllMedicineNameDistinct("Tdwadwadaw", 0)).willReturn(Collections.emptyList());

        List<MedicineNameDTO> medicineNameDTOList = medicineService.getAllMedicineNameDistinct("Tdwadwadaw", 0);

        assertThat(medicineNameDTOList).isEmpty();
        assertThat(medicineNameDTOList.size()).isEqualTo(0);
    }
}