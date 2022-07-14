package com.example.telecare.config;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.exception.ResourceNotFoundException;
import com.example.telecare.model.*;
import com.example.telecare.repository.DoctorRepository;
import com.example.telecare.repository.MedicalRecordRepository;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.service.impl.AppointmentServiceImpl;
import com.example.telecare.service.impl.NotificationServiceImpl;
import com.example.telecare.service.impl.UserServiceImpl;
import com.example.telecare.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Configuration
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
                //send notification

                try {
                    Date notificationDate = new SimpleDateFormat("yyyy-MM-dd").parse(appointmentDTO.getTime());
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    //notification for patient
                    notificationService.sendNotification(appointmentDTO.getPatientId(),
                            "Lịch khám của bạn vào ngày " + simpleDateFormat.format(notificationDate) + " đã bị hệ thống tự động huỷ.");
                    //notification for doctor
                    notificationService.sendNotification(appointmentDTO.getDoctorId(),
                            "Lịch khám của bạn vào ngày " + simpleDateFormat.format(notificationDate) + " đã bị hệ thống tự động huỷ.");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Scheduled(fixedRate = 1000 * 10)
    private void setOverdueMedicalRecord() {

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = formatter.format(cld.getTime());

        System.out.println(date);
        List<MedicalRecord> medicalRecords = medicalRecordRepository.getOverDueMedicalRecord(date);
        if (!medicalRecords.isEmpty()) {
            for (MedicalRecord medicalRecord : medicalRecords) {
                medicalRecord.setIsEdited((byte) 1);
                medicalRecordRepository.save(medicalRecord);
            }
        }
    }

    @Scheduled(fixedRate = 1000 * 60 * 60)
    private void banDoctorExpireCertificate() {
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String date = formatter.format(cld.getTime());
        List<Doctor> expireDoctor = doctorRepository.getAllExpireDoctor(date);
        if (!expireDoctor.isEmpty()) {
            for (Doctor doctor : expireDoctor) {
                User user = userRepository.findById(doctor.getDoctorId()).orElseThrow(()
                        -> new ResourceNotFoundException("Không tìm thấy người dùng"));
                user.setIsActive((byte) Constants.IS_BAN);
                user.setReason("Bác sĩ đã quá hạn chứng chỉ");
                userRepository.save(user);
            }
        }
        logger.info("Expire doctor: {}", expireDoctor.size());
    }
}
