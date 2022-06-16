package com.example.telecare.service.impl;

import com.example.telecare.dto.AppointmentDTOInf;
import com.example.telecare.dto.DoctorDTOInf;
import com.example.telecare.dto.PatientDTOInf;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.Address;
import com.example.telecare.repository.AppointmentRepository;
import com.example.telecare.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    AppointmentRepository appointmentRepository;
    @Autowired
    PatientServiceImpl patientService;
    @Autowired
    DoctorServiceImpl doctorService;

    @Autowired
    EthnicServiceImpl ethnicService;

    @Autowired
    AddressServiceImpl addressService;

    @Override
    public List<AppointmentDTOInf> findAppointmentByPatient(int id, List<Integer> statusId) {
        return appointmentRepository.findAppointmentByPatient(id, statusId);
    }

    @Override
    public AppointmentDTOInf findAppointmentById(int id) {
        AppointmentDTOInf appointmentDTO = appointmentRepository.findAppointmentDetailById(id);
        if (appointmentDTO == null) {
            throw new NotFoundException("Không tìm thấy cuộc hẹn");
        }
        PatientDTOInf patient = patientService.findPatientById(appointmentDTO.getPatientId());
        DoctorDTOInf doctor = doctorService.findDoctorById(appointmentDTO.getDoctorId());
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
            public String getSchedule() {
                return appointmentDTO.getSchedule();
            }

            @Override
            public Date getTime() {
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
            public String getPatientName() {
                return patient.getFullName();
            }

            @Override
            public String getPatientImageUrl() {
                return patient.getImageUrl();
            }

            @Override
            public Byte getPatientGender() {
                return patient.getGender();
            }

            @Override
            public String getPatientPhone() {
                return patient.getPhone();
            }

            @Override
            public Date getPatientDob() {
                return patient.getDob();
            }

            @Override
            public String getPatientEthnic() {

                return patient.getEthnicId() == null ? null
                        : ethnicService.findEthnicById(patient.getEthnicId()).getName();
            }

            @Override
            public String getPatientEmail() {
                return patient.getEmail();
            }

            @Override
            public String getPatientJobPlace() {
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
