package com.example.telecare.service.impl;

import com.example.telecare.exception.BadRequestException;
import com.example.telecare.model.Role;
import com.example.telecare.model.User;
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

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User registerPatient(User user) {
        User duplicateUserByPhone = userRepository.findUserByPhone(user.getPhone());
        User duplicateUserByEmail = userRepository.findUserByEmail(user.getEmail());
        if (duplicateUserByPhone != null) {
            logger.error("{} is existed", duplicateUserByPhone.getPhone());
            throw new BadRequestException("Phone is already existed");
        } else if (duplicateUserByEmail != null) {
            logger.error("{} is existed", duplicateUserByEmail.getEmail());
            throw new BadRequestException("Email is already existed");
        } else {
            encodePassword(user);
            logger.info("Save user to database");
            Role rolePatient = roleRepository.findByName(ProjectStorage.ROLE_PATIENT);
            user.addRole(rolePatient);
            User registeredUser = userRepository.save(user);
            return registeredUser;
        }
    }

    @Override
    public User registerDoctor(User user) {
        User duplicateUserByPhone = userRepository.findUserByPhone(user.getPhone());
        User duplicateUserByEmail = userRepository.findUserByEmail(user.getEmail());
        if (duplicateUserByPhone != null) {
            logger.error("{} is existed", duplicateUserByPhone.getPhone());
            throw new BadRequestException("Phone is already existed");
        } else if (duplicateUserByEmail != null) {
            logger.error("{} is existed", duplicateUserByEmail.getEmail());
            throw new BadRequestException("Email is already existed");
        } else {
            encodePassword(user);
            logger.info("Save user to database");
            Role rolePatient = roleRepository.findByName(ProjectStorage.ROLE_DOCTOR);
            user.addRole(rolePatient);
            User registeredUser = userRepository.save(user);
            return registeredUser;
        }
    }

    @Override
    public void encodePassword(User user) {
        UUID randomUUID = UUID.randomUUID();
        String salt = randomUUID.toString().replaceAll("-", "").substring(0, 7);
        String encodePass = passwordEncoder.encodePasswordAlgorithm(salt, user.getPassword());
        logger.info("Password encrypted");
        user.setPassword(encodePass);
        user.setPasswordSalt(salt);
        logger.info("User has been registered");
    }
}
