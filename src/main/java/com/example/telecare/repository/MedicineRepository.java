package com.example.telecare.repository;

import com.example.telecare.dto.MedicineDTO;
import com.example.telecare.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine,Integer> {
    @Query(value = "SELECT * FROM telecare.medicine  limit ?1,50",nativeQuery = true)
    List<MedicineDTO> getAllMedicineDTO(int index);
}
