package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.AppointmentDTOInf;
import com.example.telecare.dto.interfaces.AppointmentDTOInfForAdmin;
import com.example.telecare.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT a.id \n" +
            "            , u.image_url as doctorImageUrl ,\n" +
            "            u.full_name as doctorName, u.phone as doctorPhone," +
            "            a.patient_id as patientId, spec.name as doctorSpecialty,a.relative_id as relativeId,\n" +
            "            s.start_at as startAt,s.end_at as endAt,a.doctor_id as doctorId \n" +
            "            ,DATE_FORMAT (ad.time,'%d-%m-%Y') as time ,aps.name as status,aps.id as statusId\n" +
            "            FROM telecare.appointment a\n" +
            "            left outer join telecare.doctor_specialty ds on ds.doctor_id = a.doctor_id\n" +
            "            left outer join telecare.specialty spec on spec.id = ds.specialty_id\n" +
            "            left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "            left outer join telecare.user u on a.doctor_id = u.id\n" +
            "            left outer join telecare.schedule s on a.schedule_id = s.id\n" +
            "            left outer join telecare.appointment_status aps on aps.id = ad.status_id\n" +
            "            left outer join telecare.cancel_appointment ca on ca.appointment_id = a.id\n" +
            "            where a.patient_id = ?1 and aps.id in (?2) group by s.end_at,s.start_at,ad.time\n" +
            "                        order by \n" +
            "                               CASE WHEN aps.id in (1,2) THEN ad.time END,\n" +
            "                               CASE WHEN aps.id in (3) THEN ad.time END DESC,\n" +
            "                               CASE WHEN aps.id in (4) THEN ca.id END DESC",
            nativeQuery = true)
    List<AppointmentDTOInf> findAppointmentByPatient(int id, List<Integer> statusId);

    @Query(value = "SELECT Count(*) FROM telecare.appointment a left outer join telecare.appointment_details ad\n" +
            "on a.id = ad.appointment_id\n" +
            " where a.payment_status_id = 1 and a.patient_id = ?1 and ad.status_id != 4;",
            nativeQuery = true)
    Integer countAppointmentPendingPaymentByPatientId(int id);

    @Query(value = "SELECT a.id , u.id as doctorId ,p.patient_id as patientId\n" +
            "                        ,a.relative_id as relativeId ,u.full_name as doctorName, spec.name as doctorSpecialty,\n" +
            "                                    a.is_share_medical_record as isShare,ad.description " +
            "                                   ,s.start_at as startAt,s.end_at as endAt,a.is_add_medical_record as isAdd\n" +
            "                                   ,ad.time,aps.name as status,aps.id as statusId,ad.amount\n" +
            "                                   ,mr.url as medicalRecordUrl, pre.url as prescriptionUrl\n" +
            "                                    FROM telecare.appointment a\n" +
            "                                    left outer join telecare.patient p on a.patient_id = p.patient_id\n" +
            "                                    left outer join telecare.doctor_specialty ds on ds.doctor_id = a.doctor_id\n" +
            "                                    left outer join telecare.specialty spec on spec.id = ds.specialty_id\n" +
            "                                    left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "                                    left outer join telecare.user u on a.doctor_id = u.id\n" +
            "                                    left outer join telecare.schedule s on a.schedule_id = s.id\n" +
            "                                    left outer join telecare.appointment_status aps on aps.id = ad.status_id\n" +
            "                                    left outer join telecare.medical_record mr on a.id=mr.appointment_id\n" +
            "                                   left outer join telecare.prescription pre on a.id=pre.appointment_id\n" +
            "                                    where a.id = ?1\n" +
            "                                    group by s.end_at,s.start_at,ad.time",
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
            "    ad.description,a.relative_id as relativeId,\n" +
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
            "        LEFT OUTER JOIN\n" +
            "    telecare.cancel_appointment ca on ca.appointment_id = a.id\n" +
            "WHERE\n" +
            "    d.doctor_id = ?1\n" +
            "        AND aps.id IN (?2)\n" +
            "GROUP BY s.end_at , s.start_at , ad.time\n" +
            "ORDER BY           CASE WHEN aps.id in (1,2) THEN ad.time END,\n" +
            "                   CASE WHEN aps.id in (3) THEN ad.time END DESC,\n" +
            "                   CASE WHEN aps.id in (4) THEN ca.id END DESC\n",
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

    @Query(value = "SELECT a.id , u.id as doctorId ,p.patient_id as patientId\n" +
            "                                    ,a.relative_id as relativeId ,u.full_name as doctorName, spec.name as doctorSpecialty,\n" +
            "                                                ad.description ,s.start_at as startAt,s.end_at as endAt\n" +
            "                                    ,ad.time,aps.name as status,aps.id as statusId,ad.amount\n" +
            "                                                FROM telecare.appointment a\n" +
            "                                                left outer join telecare.patient p on a.patient_id = p.patient_id\n" +
            "                                                left outer join telecare.doctor_specialty ds on ds.doctor_id = a.doctor_id\n" +
            "                                                left outer join telecare.specialty spec on spec.id = ds.specialty_id\n" +
            "                                                left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            "                                                left outer join telecare.user u on a.doctor_id = u.id\n" +
            "                                                left outer join telecare.schedule s on a.schedule_id = s.id\n" +
            "                                                left outer join telecare.appointment_status aps on aps.id = ad.status_id\n" +
            "                                                left outer join telecare.payment payment on payment.appointment_id = a.id\n" +
            "                                                where ad.status_id = 3 and   if (?3 ,a.patient_id = ?1,a.doctor_id = ?1)  and a.payment_status_id = ?2\n" +
            "                                                group by s.end_at,s.start_at,ad.time\n" +
            "                                                order by payment.transaction_date desc",
            nativeQuery = true)
    List<AppointmentDTOInf> findDoneAppointment(int userId, int paymentStatusId, boolean isPatient);

    @Query(value = "select a.id , up.full_name patientName,up.id patientId,up.phone patientPhone," +
            "ud.full_name doctorName,ud.id doctorId,p.trace prescriptionTrace," +
            "p.url prescriptionUrl,mr.trace medicalRecordTrace,mr.url medicalRecordUrl,ad.time\n" +
            "from appointment as a \n" +
            "left join user as up on a.patient_id=up.id\n" +
            "left join prescription as p on a.id = p.appointment_id\n" +
            "left join user as ud on a.doctor_id=ud.id\n" +
            "left join medical_record as mr on a.id=mr.appointment_id\n" +
            "left join appointment_details as ad on a.id=ad.appointment_id\n" +
            "where up.full_name like %?2% or ud.full_name like %?2% or " +
            "up.phone like %?2% or p.trace like %?2% or mr.trace like %?2% or ad.time like %?2% \n" +
            "limit ?1,10", nativeQuery = true)
    List<AppointmentDTOInfForAdmin> getAllAppointmentForAdmin(int index, String search);

    @Query(value = "select a.id , up.full_name patientName,up.id patientId,up.phone patientPhone,\n" +
            "            ud.full_name doctorName,ud.id doctorId,ad.time\n" +
            "            ,s.start_at startAt, s.end_at endAt,aps.name appointmentStatus,\n" +
            "            p.trace prescriptionTrace, p.url prescriptionUrl,\n" +
            "            mr.trace medicalRecordTrace,mr.url medicalRecordUrl,\n" +
            "            ps.status paymentStatus, re.full_name relativeName\n" +
            "            from appointment as a \n" +
            "            left join user as up on a.patient_id=up.id\n" +
            "            left join user as ud on a.doctor_id=ud.id\n" +
            "            left join prescription as p on a.id = p.appointment_id\n" +
            "            left join medical_record as mr on a.id=mr.appointment_id\n" +
            "            left join appointment_details as ad on a.id=ad.appointment_id\n" +
            "            left join appointment_status as aps on ad.status_id=aps.id\n" +
            "            left join schedule s on s.id = a.schedule_id\n" +
            "            left join payment_status ps on ps.id = a.payment_status_id\n" +
            "            left join relative re on re.id = a.relative_id\n" +
            "            where a.id = ?1", nativeQuery = true)
    AppointmentDTOInfForAdmin getAppointmentDetailForAdmin(int appointmentId);


    @Query(value = "select  count(*)\n" +
            "from appointment as a \n" +
            "left join user as up on a.patient_id=up.id\n" +
            "left join prescription as p on a.id = p.appointment_id\n" +
            "left join user as ud on a.doctor_id=ud.id\n" +
            "left join medical_record as mr on a.id=mr.appointment_id\n" +
            "left join appointment_details as ad on a.id=ad.appointment_id\n" +
            "where up.full_name like %?1% or ud.full_name like %?1% or up.phone like %?1% or p.trace like %?1% or mr.trace like %?1% or ad.time like %?1% "
            ,nativeQuery = true)
    int getNumberOfAppointmentForAdmin(String search);

    @Query(value = "select a.id , up.full_name patientName,up.id patientId,\n" +
            "                        ud.full_name doctorName,ud.id doctorId,ad.time\n" +
            "                        ,s.start_at startAt, s.end_at endAt,aps.name appointmentStatus\n" +
            "                        from appointment as a \n" +
            "                        left join user as up on a.patient_id=up.id\n" +
            "                        left join user as ud on a.doctor_id=ud.id\n" +
            "                        left join appointment_details as ad on a.id=ad.appointment_id\n" +
            "                        left join appointment_status as aps on ad.status_id=aps.id\n" +
            "                        left join schedule s on s.id = a.schedule_id\n" +
            "                        where  up.full_name like %?2% or ud.full_name like %?2% or ad.time like %?2% or aps.name like %?2%\n" +
            "                        order by a.created_at desc\n" +
            "                        limit ?1,10", nativeQuery = true)
    List<AppointmentDTOInfForAdmin> getAllAppointmentDetailsForAdmin(int index, String search);

    @Query(value = "select count(*) from appointment as a \n" +
            "                        left join user as up on a.patient_id=up.id\n" +
            "                        left join user as ud on a.doctor_id=ud.id\n" +
            "                        left join appointment_details as ad on a.id=ad.appointment_id\n" +
            "                        left join appointment_status as aps on ad.status_id=aps.id\n" +
            "                        left join schedule s on s.id = a.schedule_id\n" +
            "                        where  up.full_name like %?1% or ud.full_name like %?1% or ad.time like %?1% or aps.name like %?1%"
            ,nativeQuery = true)
    int getNumberOfAppointmentDetailsForAdmin(String search);


}
