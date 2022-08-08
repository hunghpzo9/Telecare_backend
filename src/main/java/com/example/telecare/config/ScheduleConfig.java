package com.example.telecare.config;

import com.example.telecare.dto.interfaces.AppointmentDTOInf;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.*;
import com.example.telecare.repository.DoctorRepository;
import com.example.telecare.repository.MedicalRecordRepository;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.service.impl.AppointmentServiceImpl;
import com.example.telecare.service.impl.NotificationServiceImpl;
import com.example.telecare.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

//@Configuration
@EnableScheduling
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
    private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    @Scheduled(fixedRate = 1000 * 60)
    private void cancelAppointmentTask() {
        List<AppointmentDTOInf> appointmentList = appointmentService.findAppointmentOverdue();
        if (!appointmentList.isEmpty()) {
            for (AppointmentDTOInf appointmentDTO : appointmentList) {
                CancelAppointment cancelAppointment = new CancelAppointment();

                cancelAppointment.setUserId(appointmentDTO.getDoctorId());
                cancelAppointment.setCancelReasonId(Constants.SYSTEM_CANCEL_STATUS);
                cancelAppointment.setAppointmentId(appointmentDTO.getId());
                cancelAppointment.setDescription("Hệ thống tự động huỷ");

                appointmentService.cancelAppointment(cancelAppointment, appointmentDTO.getDoctorId());
                logger.info("Cancel appointment id: {}", appointmentDTO.getId());
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
            }
        }
        logger.info("Expire doctor: {}", expireDoctor.size());
    }
}
