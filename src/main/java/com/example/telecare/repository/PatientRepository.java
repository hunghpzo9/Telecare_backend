package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.PatientDTOInf;
import com.example.telecare.dto.interfaces.PatientDTOAdminInf;
import com.example.telecare.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PatientRepository extends JpaRepository<Patient,Integer> {
    @Query(value = "SELECT u.id,u.full_name as fullName,u.date_of_birth as dob ,u.email as email,\n" +
            "countTable.countDoneAppointment,\n" +
            "u.phone as phone,u.gender as gender,u.image_url as imageUrl,\n" +
            "a.street_name as streetName,\n" +
            "e.id as ethnicId, u.is_active as isActive ,u.reason,\n" +
            "p.blood_type as bloodType,p.height as height,p.weight as weight,p.job as job,\n" +
            "p.job_place as jobPlace,p.insurance,\n" +
            "a.id as addressId ,a.city_id as cityId,a.district_id as districtId,a.ward_id as wardId\n" +
            "FROM user u left outer join patient p ON u.id = p.patient_id \n" +
            "left outer join address a on a.id = u.address_id\n" +
            "left outer join ethnic e on p.ethnic_id = e. id,\n" +
            "(select COUNT(*) as countDoneAppointment FROM appointment a left outer join appointment_details ad ON a.id = ad.appointment_id\n" +
            "WHERE patient_id = ?1 and ad.status_id = 3 ) as countTable\n" +
            "WHERE u.id = ?1",
            nativeQuery = true)
    PatientDTOInf findPatientById(int uid);

    @Query(value = "SELECT u.id,u.full_name as fullName,u.date_of_birth as dob ,u.email as email,\n" +
            "           u.phone as phone,u.gender as gender,u.image_url as imageUrl,\n" +
            "            a.street_name as streetName,\n" +
            "            e.name as ethnicName,\n" +
            "            p.blood_type as bloodType,p.height as height,p.weight as weight,p.job as job,p.job_place as jobPlace,\n" +
            "            c.name as cityName,d.name as districtName,w.name as wardName,u.is_active as isActive,u.reason as reason\n" +
            "            FROM user u   join patient p ON u.id = p.patient_id\n" +
            "            left outer join address a on a.id = u.address_id\n" +
            "            left outer join ethnic e on p.ethnic_id = e. id\n" +
            "            left outer join city c on a.city_id=c.id\n" +
            "            left outer join district d on a.district_id=d.id\n" +
            "            left outer join ward w on a.ward_id=w.id\n"+
            "            WHERE u.id = ?1"
            ,nativeQuery = true)
    PatientDTOAdminInf findPatientByIdForAdmin(int uid);

    @Query(value = "SELECT u.id,u.full_name as fullName,u.date_of_birth as dob ,u.email as email,\n" +
            "           u.phone as phone,u.gender as gender,u.image_url as imageUrl,\n" +
            "            a.street_name as streetName,\n" +
            "            e.name as ethnicName,\n" +
            "            p.blood_type as bloodType,p.height as height,p.weight as weight,p.job as job,p.job_place as jobPlace,\n" +
            "            c.name as cityName,d.name as districtName,w.name as wardName,u.is_active as isActive,u.reason as reason\n" +
            "            FROM user u   join patient p ON u.id = p.patient_id\n" +
            "            left outer join address a on a.id = u.address_id\n" +
            "            left outer join ethnic e on p.ethnic_id = e. id\n" +
            "            left outer join city c on a.city_id=c.id\n" +
            "            left outer join district d on a.district_id=d.id\n" +
            "            left outer join ward w on a.ward_id=w.id\n" +
            "            where u.full_name like %?2% or u.phone like %?2% or p.job_place like %?2% or p.job like %?2% order by u.created_at desc\n" +
            "            limit ?1,10",
            nativeQuery = true)
    List<PatientDTOAdminInf> getAllPatient(int index, String search);

    @Query(value = " SELECT count(*)\n" +
            "            FROM user u   join patient p ON u.id = p.patient_id\n" +
            "            left outer join address a on a.id = u.address_id\n" +
            "            left outer join ethnic e on p.ethnic_id = e. id\n" +
            "            left outer join city c on a.city_id=c.id\n" +
            "            left outer join district d on a.district_id=d.id\n" +
            "            left outer join ward w on a.ward_id=w.id\n" +
            "           where u.full_name like %?1% or u.phone like %?1% or p.job_place like %?1% or p.job like %?1%"
            ,nativeQuery = true)
    int getNumberOfPatient(String search);

    @Query(value = "select COUNT(*) FROM appointment a left outer join appointment_details ad ON a.id = ad.appointment_id\n" +
            " WHERE patient_id = ?1 and ad.status_id = 3"
            ,nativeQuery = true)
    int getTotalDoneAppointmentByPatient(int patientId);
}
