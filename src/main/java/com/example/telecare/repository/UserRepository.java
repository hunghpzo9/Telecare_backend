package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.AdminDTOInf;
import com.example.telecare.dto.interfaces.AdminDashboardDTOInf;
import com.example.telecare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM telecare.user WHERE phone = ?1",
            nativeQuery = true)
    User findUserByPhone(String phone);

    @Query(value = "SELECT * FROM telecare.user WHERE email = ?1",
            nativeQuery = true)
    User findUserByEmail(String email);
    @Query(value = "SELECT * FROM telecare.user WHERE id = ?1",
            nativeQuery = true)
    User findUserById(String uid);
    @Query(value = "SELECT u.id as id,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.phone as phone,u.email as email FROM telecare.user as u where id=?1",
            nativeQuery = true)
    AdminDTOInf findAdminById(int id);

    @Query(value = "SELECT\n" +
            "  (SELECT COUNT(*) FROM telecare.patient) numberOfPatient, \n" +
            "  (SELECT COUNT(*) FROM telecare.doctor) numberOfDoctor,\n" +
            "  (SELECT COUNT(*) FROM telecare.patient p \n" +
            "  left outer join telecare.user u on p.patient_id = u.id where u.is_active = 1) numberOfActivePatient,\n" +
            "\t(SELECT COUNT(*) FROM telecare.doctor d\n" +
            "  left outer join telecare.user u on d.doctor_id = u.id where u.is_active = 1) numberOfActiveDoctor,\n" +
            "    (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= ?1 and status = 1) totalAmountThisMonth,\n" +
            "\t(SELECT COUNT(*) FROM telecare.appointment a\n" +
            " left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            " left outer join telecare.appointment_status aps on ad.status_id = aps.id\n" +
            " where a.payment_status_id = 2 ) numberOfPaidAppointment,\n" +
            "\t(SELECT COUNT(*) FROM telecare.appointment a\n" +
            " left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            " where a.payment_status_id = 1 and ad.status_id = 3) numberOfUnpaidAppointment,\n" +
            "\t(SELECT COUNT(*) FROM telecare.appointment a\n" +
            " left outer join telecare.appointment_details ad on a.id = ad.appointment_id\n" +
            " where ad.status_id = 4) numberOfCancelAppointment,\n" +
            "\t(SELECT value FROM telecare.listed_price where is_use = 0 order by created_at desc LIMIT 1) currentListedPrice,\n" +
            "\t(SELECT COUNT(*) FROM telecare.user u right outer join telecare.patient p on u.id = p.patient_id\n" +
            "WHERE CASE \n" +
            "\tWHEN 7>=1 and 7<=3 THEN month(created_at) >=1 and month(created_at) <=3 and YEAR(created_at) = 2022\n" +
            "    WHEN 7>=4 and 7<=6 THEN month(created_at) >=4 and month(created_at) <=6 and YEAR(created_at)= 2022\n" +
            "    WHEN 7>=7 and 7<=9 THEN month(created_at) >=7 and month(created_at) <=9 and YEAR(created_at)= 2022\n" +
            "    WHEN 7>=10 and 7<=12 THEN month(created_at) >=10 and month(created_at) <=12 and YEAR(created_at)= 2022\n" +
            "    END ) as totalNewPatientThisQuarter,\n" +
            "(SELECT COUNT(*) FROM telecare.user u right outer join telecare.doctor d on u.id = d.doctor_id\n" +
            "WHERE CASE \n" +
            "\tWHEN 7>=1 and 7<=3 THEN month(created_at) >=1 and month(created_at) <=3 and YEAR(created_at) = 2022\n" +
            "    WHEN 7>=4 and 7<=6 THEN month(created_at) >=4 and month(created_at) <=6 and YEAR(created_at)= 2022\n" +
            "    WHEN 7>=7 and 7<=9 THEN month(created_at) >=7 and month(created_at) <=9 and YEAR(created_at)= 2022\n" +
            "    WHEN 7>=10 and 7<=12 THEN month(created_at) >=10 and month(created_at) <=12 and YEAR(created_at)= 2022\n" +
            "    END) as totalNewDoctorThisQuarter,\n" +
            " (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 1 and status = 1) totalAmountJan,\n" +
            " (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 2 and status = 1) totalAmountFeb,\n" +
            " (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 3 and status = 1) totalAmountMar,\n" +
            " (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 4 and status = 1) totalAmountApr,\n" +
            "  (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 5 and status = 1) totalAmountMay,\n" +
            " (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 6 and status = 1) totalAmountJune,\n" +
            "  (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 7 and status = 1) totalAmountJuly,\n" +
            " (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 8 and status = 1) totalAmountAug,\n" +
            "  (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 9 and status = 1) totalAmountSep,\n" +
            " (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 10 and status = 1) totalAmountOct,\n" +
            "  (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 11 and status = 1) totalAmountNov,\n" +
            " (SELECT COALESCE(SUM(amount),0) FROM telecare.payment where month (CAST(transaction_date as date))= 12 and status = 1) totalAmountDec\n" +
            "  ;",
            nativeQuery = true)
    AdminDashboardDTOInf getDashboard(int currentMonth,int currentYear);

}
