package com.example.telecare.repository;

import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DoctorRepository  extends JpaRepository<Doctor,Integer> {
    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "            ,d.position as position ,d.job_place as jobPlace,s.name as specialty FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "            left outer join telecare.specialty s on ds.specialty_id = s.id \n" +
            "            where d.doctor_id = ?1\n" +
            "            group by u.id",
            nativeQuery = true)
    DoctorDTOInf findDoctorById(int uid);

    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "            ,d.position as position ,d.job_place as jobPlace,s.name as specialty FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "            left outer join telecare.specialty s on ds.specialty_id = s.id \n" +
            "            where (u.full_name like ?1 or d.job_place like ?1) and  s.id = ?2\n" +
            "            group by u.id \n" +
            "            limit 3\n" +
            "            offset ?3",
            nativeQuery = true)
    List<DoctorDTOInf> listAllDoctorBySpecialty(String search,int specialtyId,int page);

    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "            ,d.position as position ,d.job_place as jobPlace,s.name as specialty FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "            left outer join telecare.specialty s on ds.specialty_id = s.id \n" +
            "            where u.full_name like ?1 or d.job_place like ?1 or s.name like ?1\n" +
            "            group by u.id \n" +
            "            limit 3\n" +
            "            offset ?2",
            nativeQuery = true)
    List<DoctorDTOInf> listAllDoctor(String search,int page);
}
