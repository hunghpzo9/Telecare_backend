package com.example.telecare.repository;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT a.id , u.id as doctorId,p.patient_id as patientId" +
            ",a.relative_id as relativeId, u.image_url as doctorImageUrl ," +
            " u.full_name as doctorName, spec.name as doctorSpecialty,\n" +
            "ad.description ,s.start_at as startAt,s.end_at as endAt" +
            ",DATE_FORMAT (ad.time,'%d-%m-%Y') as time ,aps.name as status,aps.id as statusId\n" +
            "FROM telecare.appointment a\n" +
            "left outer join telecare.patient p on a.patient_id = p.patient_id\n" +
            "left outer join telecare.doctor_specialty ds on ds.doctor_id = a.doctor_id\n" +
            "left outer join telecare.specialty spec on spec.id = ds.specialty_id\n" +
            "left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "left outer join telecare.user u on a.doctor_id = u.id\n" +
            "left outer join telecare.schedule s on a.schedule_id = s.id\n" +
            "left outer join telecare.appointment_status aps on aps.id = ad.status_id\n" +
            "where p.patient_id = ?1 and aps.id in (?2) group by s.end_at,s.start_at,ad.time\n" +
            "            order by ad.time\n",
            nativeQuery = true)
    List<AppointmentDTOInf> findAppointmentByPatient(int id, List<Integer> statusId);
    @Query(value = "SELECT Count(*) FROM telecare.appointment a left outer join telecare.appointment_details ad\n" +
            "on a.id = ad.appointment_id\n" +
            " where a.payment_status_id = 1 and a.patient_id = ?1 and ad.status_id != 4;",
            nativeQuery = true)
    Integer countAppointmentPendingPaymentByPatientId(int id);

    @Query(value = "SELECT a.id , u.id as doctorId ,p.patient_id as patientId" +
            ",a.relative_id as relativeId ,u.full_name as doctorName, spec.name as doctorSpecialty,\n" +
            "            ad.description ,s.start_at as startAt,s.end_at as endAt" +
            ",ad.time,aps.name as status,aps.id as statusId\n" +
            "            FROM telecare.appointment a\n" +
            "            left outer join telecare.patient p on a.patient_id = p.patient_id\n" +
            "            left outer join telecare.doctor_specialty ds on ds.doctor_id = a.doctor_id\n" +
            "            left outer join telecare.specialty spec on spec.id = ds.specialty_id\n" +
            "            left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "            left outer join telecare.user u on a.doctor_id = u.id\n" +
            "            left outer join telecare.schedule s on a.schedule_id = s.id\n" +
            "            left outer join telecare.appointment_status aps on aps.id = ad.status_id\n" +
            "            where a.id = ?1\n" +
            "            group by a.doctor_id",
            nativeQuery = true)
    AppointmentDTOInf findAppointmentDetailById(int id);
    @Query(value = "SELECT a.schedule_id FROM telecare.appointment a left outer join \n" +
            "            telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "            where time = ?3 and a.patient_id = ?2 and (ad.status_id = 2 or ad.status_id = 1)\n" +
            "            \n" +
            "            UNION\n" +
            "            SELECT a.schedule_id FROM telecare.appointment a left outer join \n" +
            "            telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "            where time = ?3 and a.doctor_id = ?1 and ad.status_id = 2"
            ,
            nativeQuery = true)
    List<Integer> listScheduleFindByDoctorAndTime(int doctorId,int patientId, String time);

    @Query(value = "SELECT COUNT(*) FROM telecare.cancel_appointment ca where\n" +
            "created_at > (DATE_ADD(NOW(), INTERVAL -6048000 SECOND))\n" +
            "and user_id = ?1"
            ,
            nativeQuery = true)
    Integer countCancelAppointmentInOneWeek(int userId);
}
