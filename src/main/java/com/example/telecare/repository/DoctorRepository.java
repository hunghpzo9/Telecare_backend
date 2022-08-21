package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.DoctorDTOInf;
import com.example.telecare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "            ,d.position as position ,d.job_place as jobPlace,s.name as specialty,d.signature as signature,u.is_active as isActive" +
            "           ,d.certificate as certificate,d.identification_front as identificationFront,d.identification_back as identificationBack" +
            "           ,d.expire_date_certificate as expireDateCertificate, u.reason as reason FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "            left outer join telecare.specialty s on ds.specialty_id = s.id \n" +
            "            where d.doctor_id = ?1\n" +
            "            group by u.id",
            nativeQuery = true)
    DoctorDTOInf findDoctorById(int uid);

    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "            ,d.position as position ,d.job_place as jobPlace,s.name as specialty" +
            "            ,d.expire_date_certificate as expireDateCertificate FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "            left outer join telecare.specialty s on ds.specialty_id = s.id \n" +
            "        where (u.full_name like ?1 or d.job_place like ?1) and  s.id in (?2)\n" +
            "            and u.is_active = 1 and d.expire_date_certificate > ?4" +
            "            group by u.id \n" +
            "            limit 3\n" +
            "            offset ?3",
            nativeQuery = true)
    List<DoctorDTOInf> listAllDoctorBySpecialty(String search, List<Integer> specialtyId, int page, String date);

    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "            ,d.position as position ,d.job_place as jobPlace,s.name as specialty " +
            "           ,d.expire_date_certificate as expireDateCertificate FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "            left outer join telecare.specialty s on ds.specialty_id = s.id \n" +
            "            where (u.full_name like ?1 or d.job_place like ?1 or s.name like ?1)\n" +
            "            and u.is_active = 1 and d.expire_date_certificate > ?3" +
            "            group by u.id \n" +
            "            limit 3\n" +
            "            offset ?2",
            nativeQuery = true)
    List<DoctorDTOInf> listAllDoctor(String search, int page, String date);

    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "                        ,d.position as position ,d.job_place as jobPlace,s.name as specialty" +
            "                        ,d.expire_date_certificate as expireDateCertificate FROM telecare.user u\n" +
            "                        right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "                        left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "                        left outer join telecare.specialty s on ds.specialty_id = s.id \n" +
            "                        right outer join telecare.favorite_doctor fd on fd.doctor_id = d.doctor_id\n" +
            "                        where (u.full_name like ?1 or d.job_place like ?1 or s.name like ?1) " +
            "                        and u.is_active = 1 and fd.patient_id = ?3\n" +
            "                        group by u.id\n" +
            "                        limit 3\n" +
            "                        offset ?2",
            nativeQuery = true)
    List<DoctorDTOInf> listAllFavoriteDoctorById(String search, int page, int patientId);

    @Query(value = "SELECT Count(*) from favorite_doctor where patient_id = ?1 and doctor_id = ?2",
            nativeQuery = true)
    Integer countFavoriteDoctor(int patientId, int doctorId);

    @Query(value = "SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "            ,d.position as position ,d.job_place as jobPlace,s.name as specialty,u.is_active as isActive FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "            left outer join telecare.specialty s on ds.specialty_id = s.id \n" +
            "            where u.full_name like %?2% or d.job_place like %?2% or s.name like %?2% or u.phone like %?2%\n" +
            "            group by u.id order by u.created_at desc\n" +
            "            limit ?1,10\n",
            nativeQuery = true)
    List<DoctorDTOInf> getAllDoctor(int index, String search);

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
            "                        telecare.feedback f\n" +
            "                        left outer join telecare.appointment a on a.id = f.appointment_id\n" +
            "                        left outer join telecare.patient p on p.patient_id = a.patient_id\n" +
            "                        left outer join telecare.user u on u.id = p.patient_id where a.doctor_id = ?1",
            nativeQuery = true)
    Double getAverageRating(int uid);

    @Query(value = " SELECT COUNT(*) from (SELECT u.id ,u.phone,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.image_url as imageUrl,u.email as email\n" +
            "            ,d.position as position ,d.job_place as jobPlace,s.name as specialty,u.is_active as isActive FROM telecare.user u\n" +
            "            right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "            left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "            left outer join telecare.specialty s on ds.specialty_id = s.id \n" +
            "            where u.full_name like %?1% or d.job_place like %?1% or s.name like %?1% or u.phone like %?1%\n" +
            "            group by u.id ) t \n"
            , nativeQuery = true)
    int getNumberOfDoctor(String search);

    @Query(value = "SELECT d.* FROM telecare.doctor d left outer join user u on u.id = d.doctor_id \n" +
            "where expire_date_certificate < NOW() and u.is_active != 2;"
            , nativeQuery = true)
    List<Doctor> getAllExpireDoctor(String time);

    @Query(value = "SELECT Count(*) from doctor_specialty where doctor_id = ?1 ",
            nativeQuery = true)
    Integer countSpecialtyOfDoctor(int doctorId);

}
