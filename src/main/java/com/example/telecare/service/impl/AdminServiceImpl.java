package com.example.telecare.service.impl;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.dto.interfaces.AdminDTOInf;
import com.example.telecare.dto.interfaces.AppointmentDTOInfForAdmin;
import com.example.telecare.dto.interfaces.DoctorDTOInf;
import com.example.telecare.dto.interfaces.PatientDTOAdminInf;
import com.example.telecare.dto.interfaces.*;
import com.example.telecare.model.*;

import com.example.telecare.repository.ListedPriceRepository;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.service.AdminService;
import com.example.telecare.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    @Autowired
    FeedbackServiceImpl feedbackService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ListedPriceRepository listedPriceRepository;


    Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Override
    public List<Medicine> getAllMedicine(int index, String searchText) {
        return medicineService.getAllMedicine(index, searchText);
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
        return doctorService.getAllDoctor(index, search);
    }

    @Override
    public int getNumberOfDoctor(String search) {
        return doctorService.getNumberOfDoctor(search);
    }

    @Override
    public List<AppointmentDTOInfForAdmin> getAllAppointmentForAdmin(int index, String search) {
        return appointmentService.getAllAppointmentForAdmin(index, search);
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
        userService.updateStatus(isActive, id, expireDate, reason);
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
        return patientService.getAllPatient(index, search);
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
    public List<ReportDTOInfForAdmin> getListReportForAdmin(int index, String search) {
        return reportService.getListReportForAdmin(index, search);
    }

    @Override
    public int getNumberOfReportForAdmin(String search) {
        return reportService.getNumberOfReportForAdmin(search);
    }

    @Override
    public void updateStatusForReport(int reportId, int statusId) {
        reportService.updateStatus(reportId, statusId);
    }

    @Override
    public AppointmentDTOInfForAdmin getAppointmentDetailForAdmin(int appointmentId) {
        return appointmentService.getAppointmentDetailForAdmin(appointmentId);


    }

    @Override
    public List<AppointmentDTOInfForAdmin> getAllAppointmentDetailsForAdmin(int index, String search) {
        return appointmentService.getAllAppointmentDetailsForAdmin(index, search);
    }

    @Override
    public int getNumberOfAppointmentDetailsForAdmin(String search) {
        return appointmentService.getNumberOfAppointmentDetailsForAdmin(search);
    }

    @Override
    public Feedback findFeedBackByAppointmentId(int aid) {
        return feedbackService.findFeedBackByAppointmentId(aid);
    }

    @Override
    public void updateFeedbackStatusForAdmin(int id, Byte status) {
        feedbackService.updateFeedbackStatusForAdmin(id, status);
    }

    @Override
    public void sendNotificationToAllUser(String role, int money, String reason) {
        switch (role) {
            case Constants.ROLE_SYSTEM_ADMIN:{
                sendNotificationToAllUser("Thông báo từ hệ thống: "+reason);
                break;
            }

            case Constants.ROLE_BUSINESS_ADMIN:{
                //change status
                ListedPrice oldPrice = listedPriceRepository.getInUseListedPrice();
                oldPrice.setIsUse((byte) 1);
                listedPriceRepository.save(oldPrice);

                //set for new price
                ListedPrice newPrice = new ListedPrice();
                newPrice.setReason("Thông báo từ hệ thống: "+reason);
                newPrice.setValue(money);
                newPrice.setIsUse((byte) 0);
                listedPriceRepository.save(newPrice);
                sendNotificationToAllUser("Thông báo từ hệ thống: "+reason);
                break;
            }

        }
    }

    @Override
    public List<ListedPrice> getAllListedPriceForAdmin(int index, String search) {
        return listedPriceRepository.getAllListedPriceForAdmin(index, search);
    }

    @Override
    public int getNumberOfListedPrice(String search) {
        return listedPriceRepository.getNumberOfListedPrice(search);
    }


    public void sendNotificationToAllUser(String message) {
        List<User> allUser = userRepository.findAll();
        allUser.forEach(user -> sendNotification(user.getId(),message));

    }

}
