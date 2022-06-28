package com.example.telecare.service.impl;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.dto.CancelDTOInf;
import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.dto.PatientDTOInf;
import com.example.telecare.enums.AppointmentStatus;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.exception.ResourceNotFoundException;
import com.example.telecare.model.*;
import com.example.telecare.repository.AppointmentDetailRepository;
import com.example.telecare.repository.AppointmentRepository;
import com.example.telecare.repository.CancelAppointmentRepository;
import com.example.telecare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    AppointmentDetailRepository appointmentDetailRepository;
    @Autowired
    CancelAppointmentRepository cancelAppointmentRepository;
    @Autowired
    PatientServiceImpl patientService;
    @Autowired
    DoctorServiceImpl doctorService;
    @Autowired
    RelativeServiceImpl relativeService;
    @Autowired
    EthnicServiceImpl ethnicService;

    @Autowired
    AddressServiceImpl addressService;

    @Override
    public List<AppointmentDTOInf> findAppointmentByPatient(int id, List<Integer> statusId)
    {

        List<AppointmentDTOInf> appointmentList = appointmentRepository.findAppointmentByPatient(id, statusId);
        List<AppointmentDTOInf> returnAppointmentList = new ArrayList<>();
        for (AppointmentDTOInf appointmentDTO : appointmentList){
            AppointmentDTOInf finalAppointmentDTO = appointmentDTO;
            appointmentDTO = setReturnAppointment(finalAppointmentDTO);
            returnAppointmentList.add(appointmentDTO);
        }
        return returnAppointmentList;
    }

    @Override
    public AppointmentDTOInf findAppointmentById(int id) {
        AppointmentDTOInf appointmentDTO = appointmentRepository.findAppointmentDetailById(id);
        if (appointmentDTO == null) {
            throw new NotFoundException("Không tìm thấy cuộc hẹn");
        }
        return  setReturnAppointment(appointmentDTO);
    }

    @Override
    public Appointment createNewAppointment(Appointment appointment,String description,String time) {
        Appointment newAppointment = new Appointment();
        newAppointment.setPatientId(appointment.getPatientId());
        newAppointment.setDoctorId(appointment.getDoctorId());
        newAppointment.setScheduleId(appointment.getScheduleId());

        AppointmentDetails appointmentDetails = new AppointmentDetails();
        appointmentDetails.setStatusId(AppointmentStatus.NOT_CONFIRM.status);
        appointmentDetails.setDescription(description);

        //format String time to date
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parsed = simpleDateFormat.parse(time);
            java.sql.Date sqlDate = new java.sql.Date(parsed.getTime());
            appointmentDetails.setTime(sqlDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //save to database
        appointmentDetails.setAppointment(newAppointment);
        appointmentRepository.save(newAppointment);
        appointmentDetailRepository.save(appointmentDetails);

        return  newAppointment;
    }

    @Override
    public List<Integer> listScheduleFindByDoctorAndTime(int doctorId,int patientId, String time) {
        return appointmentRepository.listScheduleFindByDoctorAndTime(doctorId,patientId,time);
    }

    @Override
    public List<CancelDTOInf> getListCancel() {
        return cancelAppointmentRepository.getListCancel();
    }

    @Override
    public void cancelAppointment(CancelAppointment cancelAppointment) {


        AppointmentDetails appointmentDetails = appointmentDetailRepository
                .findById(cancelAppointment.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found appointment"));
        Appointment appointment= appointmentRepository
                .findById(cancelAppointment.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Not found appointment"));

        if(appointmentDetails!= null){
            appointmentDetails.setStatusId(AppointmentStatus.CANCEL.status);
            appointmentDetails.setAppointment(appointment);
            appointmentDetailRepository.save(appointmentDetails);
        }
       cancelAppointmentRepository.save(cancelAppointment);
    }

    private AppointmentDTOInf setReturnAppointment(AppointmentDTOInf appointmentDTO){
        PatientDTOInf patient = patientService.findPatientById(appointmentDTO.getPatientId());
        DoctorDTOInf doctor = doctorService.findDoctorById(appointmentDTO.getDoctorId());
        Relative relative = null;
        if(appointmentDTO.getRelativeId() != null){
            relative= relativeService.findRelativeById(appointmentDTO.getRelativeId());
        }
        Relative finalRelative = relative;
        AppointmentDTOInf returnAppointment = new AppointmentDTOInf() {
            @Override
            public Integer getId() {
                return appointmentDTO.getId();
            }

            @Override
            public Integer getDoctorId() {
                return appointmentDTO.getDoctorId();
            }

            @Override
            public Integer getPatientId() {
                return appointmentDTO.getPatientId();
            }

            @Override
            public String getDescription() {
                return appointmentDTO.getDescription();
            }

            @Override
            public Time getStartAt() {
                return appointmentDTO.getStartAt();
            }

            @Override
            public Time getEndAt() {
                return appointmentDTO.getEndAt();
            }


            @Override
            public String getTime() {
                return appointmentDTO.getTime();
            }

            @Override
            public String getStatus() {
                return appointmentDTO.getStatus();
            }

            @Override
            public Integer getStatusId() {
                return appointmentDTO.getStatusId();
            }

            @Override
            public Integer getRelativeId() {
                return appointmentDTO.getRelativeId();
            }

            @Override
            public String getPatientName() {
                if(finalRelative !=null){
                    return finalRelative.getFullName();
                }
                return patient.getFullName();
            }

            @Override
            public String getPatientImageUrl() {
                return patient.getImageUrl();
            }

            @Override
            public Byte getPatientGender() {
                if(finalRelative !=null){
                    return finalRelative.getGender();
                }
                return patient.getGender();
            }

            @Override
            public String getPatientPhone() {
                if(finalRelative !=null){
                    return finalRelative.getPhone();
                }
                return patient.getPhone();
            }

            @Override
            public Date getPatientDob() {
                if(finalRelative !=null){
                    return finalRelative.getDateOfBirth();
                }
                return patient.getDob();
            }

            @Override
            public String getPatientEthnic() {

                return patient.getEthnicId() == null ? null
                        : ethnicService.findEthnicById(patient.getEthnicId()).getName();
            }

            @Override
            public String getPatientEmail() {
                if(finalRelative !=null){
                    return finalRelative.getEmail();
                }
                return patient.getEmail();
            }

            @Override
            public String getPatientAddress() {
                Address address = addressService.findAddressById(patient.getAddressId());
                return address.getStreetName();
            }

            @Override
            public String getDoctorName() {
                return doctor.getFullName();
            }

            @Override
            public String getDoctorImageUrl() {
                return doctor.getImageUrl();
            }

            @Override
            public Byte getDoctorGender() {
                return doctor.getGender();
            }

            @Override
            public String getDoctorSpecialty() {
                return doctor.getSpecialty();
            }

            @Override
            public String getDoctorPhone() {
                return doctor.getPhone();
            }

            @Override
            public String getDoctorEmail() {
                return doctor.getEmail();
            }

            @Override
            public String getDoctorJobPlace() {
                return doctor.getJobPlace();
            }
        };
        return returnAppointment;
    }
}
