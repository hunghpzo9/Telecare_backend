package com.example.telecare.service;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.dto.CancelDTOInf;
import com.example.telecare.model.Appointment;
import com.example.telecare.model.AppointmentDetails;
import com.example.telecare.model.CancelAppointment;
import com.example.telecare.model.CancelReason;

import java.util.List;

public interface AppointmentService {
    List<AppointmentDTOInf> findAppointmentByPatient(int id, List<Integer> statusId);

    List<AppointmentDTOInf> findAppointmentByDoctor(int id, List<Integer> statusId);

    AppointmentDTOInf findAppointmentById(int id);

    void createNewAppointment(Appointment appointment, String description, String time);

    List<Integer> listScheduleFindByDoctorAndTime(int doctorId,int patientId, String time);

    List<CancelDTOInf> getListCancel();

    Integer countCancelAppointmentInOneWeek(int userId);

    Integer countAppointmentPendingPaymentByPatientId(int userId);

    void cancelAppointment(CancelAppointment cancelAppointment,int userId);

    void confirmAppointment(AppointmentDetails appointmentDetails, int id);

    void endAppointment(int id);

    void writeRefuseFillReason(int id,String reason);

    AppointmentDTOInf getCurrentAppointmentAvailable(String patientPhone, String doctorPhone,String date,String time);

    List<AppointmentDTOInf> findAppointmentOverdue();
    List<AppointmentDTOInf2> getAllAppointmentForAdmin(int index,String search);

    int getNumberOfAppointmentForAdmin(String search);

    List<AppointmentDTOInf> findDoneAppointment(int patientId,int paymentStatusId);
}
