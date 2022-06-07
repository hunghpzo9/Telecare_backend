package com.example.telecare.repository;

import com.example.telecare.dto.PatientDTO;
import com.example.telecare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    @Query(value = "SELECT u.id,u.full_name as fullName,u.date_of_birth as dob ,u.email as email" +
            ",u.phone as phone,u.gender as gender,u.image_url as imageUrl,\n" +
            "a.street_name as streetName,\n" +
            "e.name as ethnic,\n" +
            "p.blood_type as bloodType,p.height as height,p.weight as weight,p.job as job,p.job_place as jobPlace,\n" +
            "a.city_id as cityId,a.districy_id as districyId,a.ward_id as wardId\n" +
            "FROM user u left outer join patient p ON u.id = p.patient_id \n" +
            "left outer join address a on a.id = u.address_id\n" +
            "left outer join ethnic e on p.ethnic_id = e. id\n" +
            "WHERE u.id = ?1",
            nativeQuery = true)
    PatientDTO findPatientById(int uid);
}
