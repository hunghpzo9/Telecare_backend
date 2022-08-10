package com.example.telecare.service.impl;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.dto.interfaces.AdminDTOInf;
import com.example.telecare.dto.interfaces.AppointmentDTOInfForAdmin;
import com.example.telecare.dto.interfaces.DoctorDTOInf;
import com.example.telecare.dto.interfaces.PatientDTOAdminInf;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.dto.interfaces.*;
import com.example.telecare.model.Medicine;

import com.example.telecare.model.Payment;
import com.example.telecare.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired

    MedicineServiceImpl medicineService;
    @Autowired
    DoctorServiceImpl doctorService;
    @Autowired
    PatientServiceImpl patientService;
    @Autowired
    AppointmentServiceImpl appointmentService;
    @Autowired
    AuthServiceImpl authService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    NotificationServiceImpl notificationService;
    @Autowired
    PaymentServiceImpl paymentService;
    @Autowired
    ReportServiceImpl reportService;


    @Override
    public List<Medicine> getAllMedicine(int index, String searchText) {
        return medicineService.getAllMedicine(index,searchText);
    }

    @Override
    public int getNumberOfMedicine(String searchText) {
        return medicineService.getNumberOfMedicine(searchText);
    }

    @Override
    public DoctorDTOInf findDoctorById(int uid) {
        return doctorService.findDoctorById(uid);
    }

    @Override
    public PatientDTOAdminInf findPatientByIdForAdmin(int uid) {
        return patientService.findPatientByIdForAdmin(uid);
    }

    @Override
    public List<DoctorDTOInf> getAllDoctor(int index, String search) {
        return doctorService.getAllDoctor(index,search);
    }

    @Override
    public int getNumberOfDoctor(String search) {
        return doctorService.getNumberOfDoctor(search);
    }

    @Override
    public List<AppointmentDTOInfForAdmin> getAllAppointmentForAdmin(int index, String search) {
        return appointmentService.getAllAppointmentForAdmin(index,search);
    }

    @Override
    public int getNumberOfAppointmentForAdmin(String search) {
        return appointmentService.getNumberOfAppointmentForAdmin(search);
    }

    @Override
    public int getNumberOfPatient(String search) {
        return patientService.getNumberOfPatient(search);
    }

    @Override
    public ResponseEntity<?> loginForAdmin(AuthenticationRequest authenticationRequest) throws Exception {
        return authService.loginForAdmin(authenticationRequest);
    }

    @Override
    public void updateStatus(Byte isActive, int id, Date expireDate, String reason) {
    userService.updateStatus(isActive,id,expireDate,reason);
    }

    @Override
    public void updateStatusForPatient(Byte isActive, int id, String reason) {
        userService.updateStatusForPatient(isActive, id, reason);
    }

    @Override
    public AdminDTOInf findAdminById(int id) {
        return userService.findAdminById(id);
    }

    @Override
    public ResponseEntity<?> changeOldPassword(String id, String oldPassword, String newPassword) {
        return authService.changeOldPassword(id, oldPassword, newPassword);
    }

    @Override
    public void sendNotification(int uid, String message) {
        notificationService.sendNotification(uid, message);
    }
    @Override
    public List<PatientDTOAdminInf> getAllPatient(int index, String search) {
        return patientService.getAllPatient(index,search);
    }

    @Override
    public List<Payment> getAllPayment(int index, String searchText) {
        return paymentService.getAllPayment(index, searchText);
    }

    @Override
    public int getNumberOfPayment(String searchText) {
        return paymentService.getNumberOfPayment(searchText);
    }

    @Override
    public List<ReportDTOInfForAdmin> getListReportForAdmin(int index,String search) {
        return reportService.getListReportForAdmin(index,search);
    }

    @Override
    public int getNumberOfReportForAdmin(String search) {
        return reportService.getNumberOfReportForAdmin(search);
    }

    @Override
    public void updateStatusForReport(int reportId, int statusId) {
         reportService.updateStatus(reportId,statusId);
    }

    @Override
    public AppointmentDTOInfForAdmin getAppointmentDetailForAdmin(int appointmentId) {
        return appointmentService.getAppointmentDetailForAdmin(appointmentId);


    }
}
