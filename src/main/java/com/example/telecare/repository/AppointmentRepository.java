package com.example.telecare.repository;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT a.id \n" +
            "            , u.image_url as doctorImageUrl ,\n" +
            "            u.full_name as doctorName, u.phone as doctorPhone," +
            "            a.patient_id as patientId, spec.name as doctorSpecialty,\n" +
            "            s.start_at as startAt,s.end_at as endAt \n" +
            "            ,DATE_FORMAT (ad.time,'%d-%m-%Y') as time ,aps.name as status,aps.id as statusId\n" +
            "            FROM telecare.appointment a\n" +
            "            left outer join telecare.doctor_specialty ds on ds.doctor_id = a.doctor_id\n" +
            "            left outer join telecare.specialty spec on spec.id = ds.specialty_id\n" +
            "            left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "            left outer join telecare.user u on a.doctor_id = u.id\n" +
            "            left outer join telecare.schedule s on a.schedule_id = s.id\n" +
            "            left outer join telecare.appointment_status aps on aps.id = ad.status_id\n" +
            "            where a.patient_id = ?1 and aps.id in (?2) group by s.end_at,s.start_at,ad.time\n" +
            "                        order by ad.time",
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
            "            group by s.end_at,s.start_at,ad.time",
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
            "created_at > (DATE_ADD(?2, INTERVAL -6048000 SECOND))\n" +
            "and user_id = ?1 and cancel_reason_id != 11"
            ,
            nativeQuery = true)
    Integer countCancelAppointmentInOneWeek(int userId,String date);

    @Query(value = "SELECT count(*) FROM telecare.appointment a left outer join \n" +
            "                        telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "                       where time = ?2 and a.doctor_id = ?1 \n" +
            "                       and (ad.status_id = 2 or ad.status_id = 1)\n" +
            "                       and a.schedule_id = ?3"
            ,
            nativeQuery = true)
    Integer countExistingAppointment(int doctorId,String time, int scheduleId);


    @Query(value = "SELECT \n" +
            "    a.id,\n" +
            "    u.id AS patientId,\n" +
            "    a.doctor_id AS doctorId,\n" +
            "    u.image_url AS patientImageUrl,\n" +
            "    u.full_name AS patientName,\n" +
            "    u.phone AS patientPhone,\n" +
            "    spec.name AS doctorSpecialty,\n" +
            "    ad.description,\n" +
            "    s.start_at AS startAt,\n" +
            "    s.end_at AS endAt,\n" +
            "    DATE_FORMAT(ad.time, '%d-%m-%Y') AS time,\n" +
            "    aps.name AS status,\n" +
            "    aps.id AS statusId\n" +
            "FROM\n" +
            "    telecare.appointment a\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.patient p ON a.patient_id = p.patient_id\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.doctor d ON a.doctor_id = d.doctor_id\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.doctor_specialty ds ON ds.doctor_id = a.doctor_id\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.specialty spec ON spec.id = ds.specialty_id\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.appointment_details ad ON a.id = ad.appointment_id\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.user u ON a.patient_id = u.id\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.schedule s ON a.schedule_id = s.id\n" +
            "        LEFT OUTER JOIN\n" +
            "    telecare.appointment_status aps ON aps.id = ad.status_id\n" +
            "WHERE\n" +
            "    d.doctor_id = ?1\n" +
            "        AND aps.id IN (?2)\n" +
            "GROUP BY s.end_at , s.start_at , ad.time\n" +
            "ORDER BY ad.time\n",
            nativeQuery = true)
    List<AppointmentDTOInf> findAppointmentByDoctor(int id, List<Integer> statusId);

    @Query(value = "SELECT a.id , u.id as doctorId ,p.patient_id as patientId\n" +
            "            ,a.relative_id as relativeId ,u.full_name as doctorName, spec.name as doctorSpecialty,\n" +
            "                        ad.description ,s.start_at as startAt,s.end_at as endAt\n" +
            "            ,ad.time,aps.name as status,aps.id as statusId\n" +
            "                        FROM telecare.appointment a\n" +
            "                        left outer join telecare.patient p on a.patient_id = p.patient_id\n" +
            "                        left outer join telecare.doctor_specialty ds on ds.doctor_id = a.doctor_id\n" +
            "                        left outer join telecare.specialty spec on spec.id = ds.specialty_id\n" +
            "                       left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "                       left outer join telecare.user u on a.doctor_id = u.id\n" +
            "                        left outer join telecare.schedule s on a.schedule_id = s.id\n" +
            "                        left outer join telecare.appointment_status aps on aps.id = ad.status_id\n" +
            "                        where a.patient_id = ?1 and a.doctor_id = ?2 and ad.status_id = 2\n" +
            "                        and time = ?3 and s.start_at <= ?4 and s.end_at >= ?4 \n" +
            "                        group by s.end_at,s.start_at,ad.time\n" +
            "                        order by ad.time",
            nativeQuery = true)
    AppointmentDTOInf getCurrentAppointmentAvailable(int patientId, int doctorId, String date, String time);

    @Query(value = "SELECT a.id \n" +
            "                        , u.image_url as doctorImageUrl ,\n" +
            "                        u.full_name as doctorName, u.phone as doctorPhone,\n" +
            "                        a.patient_id as patientId, a.doctor_id as doctorId, spec.name as doctorSpecialty,\n" +
            "                        s.start_at as startAt,s.end_at as endAt \n" +
            "                        ,DATE_FORMAT (ad.time,'%d-%m-%Y') as time ,aps.name as status,aps.id as statusId\n" +
            "                        FROM telecare.appointment a\n" +
            "                        left outer join telecare.doctor_specialty ds on ds.doctor_id = a.doctor_id\n" +
            "                        left outer join telecare.specialty spec on spec.id = ds.specialty_id\n" +
            "                        left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "                        left outer join telecare.user u on a.doctor_id = u.id\n" +
            "                        left outer join telecare.schedule s on a.schedule_id = s.id\n" +
            "                        left outer join telecare.appointment_status aps on aps.id = ad.status_id\n" +
            "                        where aps.id in (1,2) and ((ad.time < ?1 \n" +
            "                        and s.end_at <= ?2) or ad.time < ?1 )\n" +
            "                        group by s.end_at,s.start_at,ad.time\n" +
            "                                    ",
            nativeQuery = true)
    List<AppointmentDTOInf> findAppointmentOverdue(String date,String time);
}
