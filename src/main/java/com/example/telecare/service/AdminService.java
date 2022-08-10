package com.example.telecare.service;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.dto.interfaces.*;
import com.example.telecare.model.Medicine;
import com.example.telecare.model.Payment;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

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
    AppointmentDTOInfForAdmin getAppointmentDetailForAdmin(int appointmentId);
}
