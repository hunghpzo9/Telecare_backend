package com.example.telecare.service;

import com.example.telecare.dto.interfaces.AppointmentDTOInf;
import com.example.telecare.dto.interfaces.AppointmentDTOInfForAdmin;
import com.example.telecare.dto.interfaces.CancelDTOInf;
import com.example.telecare.model.Appointment;
import com.example.telecare.model.AppointmentDetails;
import com.example.telecare.model.CancelAppointment;

import java.util.List;

public interface AppointmentService {
    List<AppointmentDTOInf> findAppointmentByPatient(int id, List<Integer> statusId);

    List<AppointmentDTOInf> findAppointmentByDoctor(int id, List<Integer> statusId);

    AppointmentDTOInf findAppointmentById(int id);

    void createNewAppointment(Appointment appointment, String description, String time,List<Integer> medicalRecordId);

    List<Integer> listScheduleFindByDoctorAndTime(int doctorId,int patientId, String time);

    List<CancelDTOInf> getListCancel();

    Integer countCancelAppointmentInOneWeek(int userId);

    Integer countAppointmentPendingPaymentByPatientId(int userId);

    void cancelAppointment(CancelAppointment cancelAppointment,int userId);

    void confirmAppointment(AppointmentDetails appointmentDetails, int id);

    void endAppointment(int id);

    void writeRefuseFillReason(int id,String reason);

    void updateIsAddMedicalRecord(int appointmentId,boolean isAdd);

    AppointmentDTOInf getCurrentAppointmentAvailable(String patientPhone, String doctorPhone,String date,String time);

    List<AppointmentDTOInf> findAppointmentOverdue();

    List<AppointmentDTOInfForAdmin> getAllAppointmentForAdmin(int index, String search);

    AppointmentDTOInfForAdmin getAppointmentDetailForAdmin(int appointmentId);

    int getNumberOfAppointmentForAdmin(String search);

    List<AppointmentDTOInf> findDoneAppointment(int userId,int paymentStatusId,boolean isPatient);

    List<AppointmentDTOInfForAdmin> getAllAppointmentDetailsForAdmin(int index, String search);

    int getNumberOfAppointmentDetailsForAdmin(String search);

    int getInUseListedPrice();
}
