package com.example.telecare.service;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.dto.interfaces.*;
import com.example.telecare.model.Feedback;
import com.example.telecare.model.ListedPrice;
import com.example.telecare.model.Medicine;
import com.example.telecare.model.Payment;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface AdminService {
    List<Medicine> getAllMedicine(int index, String searchText);
    int getNumberOfMedicine(String searchText);
    DoctorDTOInf findDoctorById(int uid);
    PatientDTOAdminInf findPatientByIdForAdmin(int uid);
    List<DoctorDTOInf> getAllDoctor(int index,String search);
    int getNumberOfDoctor(String search);
    List<AppointmentDTOInfForAdmin> getAllAppointmentForAdmin(int index, String search);
    int getNumberOfAppointmentForAdmin(String search);
    int getNumberOfPatient(String search);
    ResponseEntity<?> loginForAdmin(AuthenticationRequest authenticationRequest) throws Exception;
    void updateStatus(Byte isActive, int id, Date expireDate, String reason);
    void updateStatusForPatient(Byte isActive, int id,String reason);
    AdminDTOInf findAdminById(int id);
    ResponseEntity<?> changeOldPassword(String id, String oldPassword,String newPassword);
    void sendNotification(int uid,String message);
    public List<PatientDTOAdminInf> getAllPatient(int index, String search);
    List<Payment> getAllPayment(int index, String searchText);
    int getNumberOfPayment(String searchText);
    List<ReportDTOInfForAdmin> getListReportForAdmin(int index,String search);
    int getNumberOfReportForAdmin(String search);
    void updateStatusForReport(int reportId, int statusId);
    AppointmentDTOInfForAdmin getAppointmentDetailForAdmin(int appointmentId);
    List<AppointmentDTOInfForAdmin> getAllAppointmentDetailsForAdmin(int index, String search);
    int getNumberOfAppointmentDetailsForAdmin(String search);
    Feedback findFeedBackByAppointmentId(int aid);
    void updateFeedbackStatusForAdmin(int id,Byte status);
    void sendNotificationToAllUser(String role, int money,String reason) throws ExecutionException, InterruptedException;
    List<ListedPrice> getAllListedPriceForAdmin(int index, String search);
    int getNumberOfListedPrice(String search);
    AdminDashboardDTOInf getDashboard();
}
