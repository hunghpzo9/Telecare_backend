package com.example.telecare.config;

import com.example.telecare.dto.interfaces.AppointmentDTOInf;
import com.example.telecare.enums.AppointmentStatus;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.*;
import com.example.telecare.repository.AppointmentRepository;
import com.example.telecare.repository.DoctorRepository;
import com.example.telecare.repository.MedicalRecordRepository;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.service.impl.AppointmentServiceImpl;
import com.example.telecare.service.impl.NotificationServiceImpl;
import com.example.telecare.utils.Constants;
import com.google.firebase.messaging.FirebaseMessagingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

@Configuration
@EnableScheduling
@Slf4j
public class ScheduleConfig {
    @Autowired
    AppointmentServiceImpl appointmentService;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    NotificationServiceImpl notificationService;

    @Autowired
    MedicalRecordRepository medicalRecordRepository;

    @Autowired
    AppointmentRepository appointmentRepository;


    @Scheduled(fixedRate = 1000 * 5)
    private void cancelAppointmentTask() {
        List<AppointmentDTOInf> appointmentList = appointmentService.findAppointmentOverdue();
        if (!appointmentList.isEmpty()) {
            for (AppointmentDTOInf appointmentDTO : appointmentList) {
                if(appointmentDTO.getStatusId() != AppointmentStatus.CANCEL.status){
                    CancelAppointment cancelAppointment = new CancelAppointment();

                    cancelAppointment.setUserId(appointmentDTO.getDoctorId());
                    cancelAppointment.setCancelReasonId(Constants.SYSTEM_CANCEL_STATUS);
                    cancelAppointment.setAppointmentId(appointmentDTO.getId());
                    cancelAppointment.setDescription("Hệ thống tự động huỷ");

                    appointmentService.cancelAppointment(cancelAppointment, appointmentDTO.getDoctorId());
                    log.info("Cancel appointment id: {}", appointmentDTO.getId());
                }

            }
        }
    }

    @Scheduled(fixedRate = 1000 * 10)
    private void setOverdueMedicalRecord() {

        TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(tz);
        SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = databaseFormat.format(cal.getTime());

        List<MedicalRecord> medicalRecords = medicalRecordRepository.getOverDueMedicalRecord(date);
        if (!medicalRecords.isEmpty()) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                medicalRecord.setIsEdited((byte) Constants.IS_NOT_EDITED);
                medicalRecordRepository.save(medicalRecord);
                log.info("Overdue medical record: {}", medicalRecord.getId());
            }
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    private void banDoctorExpireCertificate() {
        TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(tz);
        SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = databaseFormat.format(cal.getTime());

        List<Doctor> expireDoctor = doctorRepository.getAllExpireDoctor(date);
        if (!expireDoctor.isEmpty()) {
            for (Doctor doctor : expireDoctor) {
                User user = userRepository.findById(doctor.getDoctorId()).orElseThrow(()
                        -> new NotFoundException("Không tìm thấy người dùng"));
                user.setIsActive((byte) Constants.IS_BAN);
                user.setReason("Bác sĩ đã quá hạn chứng chỉ");
                userRepository.save(user);
                log.info("Expire doctor: {}", user.getId());
            }
        }
    }

    @Scheduled(fixedRate = 1000 * 5)
    private void sendFCMToComingAppointment() throws FirebaseMessagingException {
        List<AppointmentDTOInf> upComingApp = appointmentService.getComingAppointmentInFifteenMin();
        for (AppointmentDTOInf appointmentDTOInf : upComingApp) {
            User patient = userRepository.findUserById(appointmentDTOInf.getPatientId().toString());
            User doctor = userRepository.findUserById(appointmentDTOInf.getDoctorId().toString());

            List<String> registrationTokens = Arrays.asList(
                    patient.getFcmToken(),
                    // ...
                    doctor.getFcmToken()
            );
            notificationService.sendCloudMessaging(registrationTokens, "Thông báo", Constants.UPCOMING_APPOINTMENT_MESSAGE);
            Appointment appointment = appointmentRepository.findById(appointmentDTOInf.getId()).
                    orElseThrow(() -> new NotFoundException("Not found appointment"));
            appointment.setIsSendFcmUpcoming((byte) 1);
            appointmentRepository.save(appointment);
        }
    }

    @Scheduled(fixedRate = 1000 * 5)
    private void sendFCMToAppointmentOnTime() throws FirebaseMessagingException {
        List<AppointmentDTOInf> onTimeApp = appointmentService.getCurrentAppointmentOnTime();
        for (AppointmentDTOInf appointmentDTOInf : onTimeApp) {
            List<String> registrationTokens = Arrays.asList(
                    userRepository.getFcmTokenByUser(appointmentDTOInf.getPatientId()),
                    userRepository.getFcmTokenByUser(appointmentDTOInf.getDoctorId())
            );
            notificationService.sendCloudMessaging(registrationTokens, "Thông báo", Constants.ON_TIME_APPOINTMENT_MESSAGE);
            Appointment appointment = appointmentRepository.findById(appointmentDTOInf.getId()).
                    orElseThrow(() -> new NotFoundException("Not found appointment"));
            appointment.setIsSendFcmOntime((byte) 1);
            appointmentRepository.save(appointment);
        }
    }
}
