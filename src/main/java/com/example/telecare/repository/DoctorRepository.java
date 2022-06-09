package com.example.telecare.repository;

import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DoctorRepository  extends JpaRepository<Doctor,Integer> {
    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            ",d.position as position ,d.job_place as jobPlace FROM telecare.user u\n" +
            "left outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "where d.doctor_id = ?1",
            nativeQuery = true)
    DoctorDTOInf findDoctorById(int uid);
}
