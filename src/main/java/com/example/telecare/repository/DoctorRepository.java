package com.example.telecare.repository;

import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "            ,d.position as position ,d.job_place as jobPlace,s.name as specialty,d.signature as signature FROM telecare.user u\n" +
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
                "            where (u.full_name like ?1 or d.job_place like ?1) and  s.id in (?2)\n" +
            "            group by u.id \n" +
            "            limit 3\n" +
            "            offset ?3",
            nativeQuery = true)
    List<DoctorDTOInf> listAllDoctorBySpecialty(String search, List<Integer> specialtyId, int page);

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
    List<DoctorDTOInf> listAllDoctor(String search, int page);

    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "            ,d.position as position ,d.job_place as jobPlace,s.name as specialty,u.is_active FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "            left outer join telecare.specialty s on ds.specialty_id = s.id \n" +
            "            group by u.id \n" +
            "            limit ?1,5\n",
            nativeQuery = true)
    List<DoctorDTOInf> getAllDoctor(int index);

    @Query(value = "SELECT  Sum(CASE WHEN ad.status_id = 3 THEN 1 ELSE 0 END )\n" +
            "as appointmentDoneCount\n" +
            "FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.appointment a on d.doctor_id = a.doctor_id\n" +
            "            left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "            left outer join telecare.appointment_status apt on ad.status_id = apt.id\n" +
            "            where d.doctor_id = ?1\n" +
            "            group by d.doctor_id\n" +
            "         ",
            nativeQuery = true)
    Integer getNumberDoneAppointment(int uid);

    @Query(value = "SELECT  Sum(CASE WHEN ad.status_id = 3 or ad.status_id = 2 THEN 1 ELSE 0 END )\n" +
            "as appointmentDoneCount\n" +
            "FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.appointment a on d.doctor_id = a.doctor_id\n" +
            "            left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "            left outer join telecare.appointment_status apt on ad.status_id = apt.id\n" +
            "            where d.doctor_id = ?1\n" +
            "            group by d.doctor_id\n" +
            "         ",
            nativeQuery = true)
    Integer getNumberPatient(int uid);

    @Query(value = "SELECT avg(f.rating) as rating FROM \n" +
            "            telecare.feedback f\n" +
            "            left outer join telecare.feedback_appointment fa on f.id = fa.feedback_id\n" +
            "            left outer join telecare.appointment a on a.id = fa.apointment_id\n" +
            "            left outer join telecare.patient p on p.patient_id = a.patient_id\n" +
            "            left outer join telecare.user u on u.id = p.patient_id where a.doctor_id = ?1",
            nativeQuery = true)
    Double getAverageRating(int uid);


}
