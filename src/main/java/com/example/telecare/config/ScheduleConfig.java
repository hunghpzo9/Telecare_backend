package com.example.telecare.config;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.model.CancelAppointment;
import com.example.telecare.service.impl.AppointmentServiceImpl;
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

    @Scheduled(fixedRate = 1000 * 60)
    public void scheduleTaskWithFixedRate() {
        List<AppointmentDTOInf> appointmentList = appointmentService.findAppointmentOverdue();
        if (!appointmentList.isEmpty()) {
            for (AppointmentDTOInf appointmentDTO : appointmentList) {
                CancelAppointment cancelAppointment = new CancelAppointment();

                cancelAppointment.setUserId(appointmentDTO.getDoctorId());
                cancelAppointment.setCancelReasonId(10);
                cancelAppointment.setAppointmentId(appointmentDTO.getId());
                cancelAppointment.setDescription("Hệ thống tự động huỷ");

                appointmentService.cancelAppointment(cancelAppointment, appointmentDTO.getDoctorId());
            }
        }

        System.out.println(appointmentList.size());
    }
}
