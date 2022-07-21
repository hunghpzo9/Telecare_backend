package com.example.telecare.repository;

import com.example.telecare.dto.MedicineNameDTO;
import com.example.telecare.dto.MedicinePrescriptionDTO;
import com.example.telecare.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    @Query(value = "SELECT * FROM telecare.medicine as m" +
            "            where m.number_of_receipts like %?2% or m.year_of_receipts like %?2% or m.name like %?2% or m.company like %?2% or m.registration_number like %?2%" +
            "            limit ?1,50\n", nativeQuery = true)
    List<Medicine> getAllMedicine(int index, String searchText);

    @Query(value = "SELECT DISTINCT\n" +
            "    Name\n" +
            "FROM\n" +
            "    telecare.medicine\n" +
            "WHERE\n" +
            "    name LIKE %?1% AND status = 0", nativeQuery = true)
    List<MedicineNameDTO> getAllMedicineNameDistinct(String searchText);

    @Query(value = "SELECT \n" +
            "    m.id, m.name, pm.instruction\n " +
            "FROM\n" +
            "    telecare.medicine m\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.prescription_medicine pm ON pm.medicine_id = m.id\n" +
            "WHERE\n" +
            "    pm.prescription_id = ?1", nativeQuery = true)
    List<MedicinePrescriptionDTO> findAllMedicineByAppointmentId(int id);

    @Query(value = "SELECT * from telecare.medicine WHERE id = ?1", nativeQuery = true)
    Medicine findMedicineById(int id);

    @Query(value = "SELECT count(*) FROM telecare.medicine as m" +
            "            where m.number_of_receipts like %?1% or m.year_of_receipts like %?1% or m.name like %?1% or m.company like %?1% or m.registration_number like %?1%"
            , nativeQuery = true)
    int getNumberOfMedicine(String searchText);

}
