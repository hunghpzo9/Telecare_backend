package com.example.telecare.config;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.model.CancelAppointment;
import com.example.telecare.service.impl.AppointmentServiceImpl;
import com.example.telecare.service.impl.UserServiceImpl;
import com.example.telecare.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableScheduling
public class ScheduleConfig {
    @Autowired
    AppointmentServiceImpl appointmentService;
    private static final Logger logger = LoggerFactory.getLogger(ScheduleConfig.class);

    @Scheduled(fixedRate = 1000 * 60)
    public void cancelAppointmentTask() {
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
}
