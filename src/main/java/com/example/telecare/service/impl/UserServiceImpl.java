package com.example.telecare.service.impl;

import com.example.telecare.dto.AdminDTOInf;
import com.example.telecare.dto.DoctorDTO;
import com.example.telecare.exception.BadRequestException;
import com.example.telecare.exception.ResourceNotFoundException;
import com.example.telecare.model.*;
import com.example.telecare.repository.*;
import com.example.telecare.security.PasswordHashService;
import com.example.telecare.service.UserService;
import com.example.telecare.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    SpecialtyRepository specialtyRepository;
    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    PasswordHashService passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PatientRepository patientRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User registerPatient(User user) {
        User duplicateUserByPhone = userRepository.findUserByPhone(user.getPhone());
        User duplicateUserByEmail = userRepository.findUserByEmail(user.getEmail());
        if (duplicateUserByPhone != null) {
            logger.error("{} is existed", duplicateUserByPhone.getPhone());
            throw new BadRequestException("Số điện thoại đã tồn tại");
        } else if (duplicateUserByEmail != null) {
            logger.error("{} is existed", duplicateUserByEmail.getEmail());
            throw new BadRequestException("Email đã tồn tại");
        } else {

            Address address = new Address();
            logger.info("Save address to database");
            user.setAddress(address);

            encodePassword(user);
            logger.info("Save user to database");
            Role rolePatient = roleRepository.findByName(Constants.ROLE_PATIENT);
            user.addRole(rolePatient);

            Patient patient = new Patient();
            patient.setUser(user);
            user.setPatient(patient);
            logger.info("Save patient to database");

            return userRepository.save(user);
        }
    }

    @Override
    public void registerDoctor(DoctorDTO doctorDTO) {
        User duplicateUserByPhone = userRepository.findUserByPhone(doctorDTO.getPhone());
        User duplicateUserByEmail = userRepository.findUserByEmail(doctorDTO.getEmail());
        if (duplicateUserByPhone != null) {
            logger.error("{} is existed", duplicateUserByPhone.getPhone());
            throw new BadRequestException("Số điện thoại đã tồn tại");
        } else if (duplicateUserByEmail != null) {
            logger.error("{} is existed", duplicateUserByEmail.getEmail());
            throw new BadRequestException("Email đã tồn tại");
        } else {
//add address
            Address address = new Address();
            logger.info("Save address to database");
            addressRepository.save(address);

//add user
            User user = new User();
            user.setFullName(doctorDTO.getFullName());
            user.setEmail(doctorDTO.getEmail());
            user.setPhone(doctorDTO.getPhone());
            user.setPassword(doctorDTO.getPassword());
            encodePassword(user);
            user.setIsActive(doctorDTO.getIsActive());
            Role roleDoctor = roleRepository.findByName(Constants.ROLE_DOCTOR);
            user.addRole(roleDoctor);
            user.setAddress(address);
            logger.info("Save user to database");
            userRepository.save(user);

            //add doctor
            Doctor doctor = new Doctor();
            doctor.setUser(user);
            doctor.setCertificate(doctorDTO.getCertificate());
            doctor.setJobPlace(doctorDTO.getJobPlace());
            doctor.setPosition(doctorDTO.getPosition());
            doctor.setIdentificationFront(doctorDTO.getIdentificationFront());
            doctor.setIdentificationBack(doctorDTO.getIdentificationBack());

            Specialty specialty = specialtyRepository.findById(doctorDTO.getSpecialtyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Not found patient"));
            doctor.addSpecialty(specialty);
            doctorRepository.save(doctor);

        }
    }

    @Override
    public User registerAdmin(User user) {
        User duplicateUserByPhone = userRepository.findUserByPhone(user.getPhone());
        User duplicateUserByEmail = userRepository.findUserByEmail(user.getEmail());
        if (duplicateUserByPhone != null) {
            logger.error("{} is existed", duplicateUserByPhone.getPhone());
            throw new BadRequestException("Số điện thoại đã tồn tại");
        } else if (duplicateUserByEmail != null) {
            logger.error("{} is existed", duplicateUserByEmail.getEmail());
            throw new BadRequestException("Email đã tồn tại");
        } else {

            Address address = new Address();
            logger.info("Save address to database");
            user.setAddress(address);

            encodePassword(user);
            logger.info("Save user to database");
            Role roleAdmin = roleRepository.findByName(Constants.ROLE_ADMIN);
            user.addRole(roleAdmin);

            return userRepository.save(user);
        }
    }

    @Override
    public void updateStatus(Byte isActive, int id,Date expireDate) {

            Doctor doctor = doctorRepository.findById(id).orElseThrow(()
                    -> new ResourceNotFoundException("Không tìm thấy bác sĩ"));
            doctor.setExpireDateCertificate(expireDate);
            doctorRepository.save(doctor);

        User user = userRepository.findById(id) .orElseThrow(()
                -> new ResourceNotFoundException("Không tìm thấy người dùng"));
        user.setIsActive(isActive);
        userRepository.save(user);
    }

    @Override
    public User findUserById(int id) {
        User user = userRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Không tìm thấy người dùng"));
        return user;
    }

    @Override
    public AdminDTOInf findAdminById(int id) {
        AdminDTOInf admin = userRepository.findAdminById(id);
        return admin;
    }


    private void encodePassword(User user) {
        UUID randomUUID = UUID.randomUUID();
        String salt = randomUUID.toString().replaceAll("-", "").substring(0, 7);
        String encodePass = passwordEncoder.encodePasswordAlgorithm(salt, user.getPassword());
        logger.info("Password encrypted");
        user.setPassword(encodePass);
        user.setPasswordSalt(salt);
        logger.info("User has been registered");
    }


}
