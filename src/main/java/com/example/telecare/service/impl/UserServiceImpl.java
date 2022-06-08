package com.example.telecare.service.impl;

import com.example.telecare.exception.BadRequestException;
import com.example.telecare.model.*;
import com.example.telecare.repository.PatientRepository;
import com.example.telecare.repository.RoleRepository;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.repository.UserRoleRepository;
import com.example.telecare.security.PasswordHashService;
import com.example.telecare.service.UserService;
import com.example.telecare.utils.ProjectStorage;
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
            Role rolePatient = roleRepository.findByName(ProjectStorage.ROLE_PATIENT);
            user.addRole(rolePatient);

            Patient patient = new Patient();
            patient.setUser(user);
            user.setPatient(patient);
            logger.info("Save patient to database");

            return userRepository.save(user);
        }
    }

    @Override
    public User registerDoctor(User user) {
        User duplicateUserByPhone = userRepository.findUserByPhone(user.getPhone());
        User duplicateUserByEmail = userRepository.findUserByEmail(user.getEmail());
        if (duplicateUserByPhone != null) {
            logger.error("{} is existed", duplicateUserByPhone.getPhone());
            throw new BadRequestException("Số điện thoại đã tồn tại");
        } else if (duplicateUserByEmail != null) {
            logger.error("{} is existed", duplicateUserByEmail.getEmail());
            throw new BadRequestException("Email đã tồn tại");
        }  else {

            Address address = new Address();
            logger.info("Save address to database");
            user.setAddress(address);

            encodePassword(user);
            logger.info("Save user to database");
            Role roleDoctor = roleRepository.findByName(ProjectStorage.ROLE_DOCTOR);
            user.addRole(roleDoctor);

            Doctor doctor = new Doctor();
            doctor.setUser(user);
            user.setDoctor(doctor);
            return userRepository.save(user);
        }
    }

    @Override
    public void updateUser(User user) {

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
