package com.example.telecare.service;

import com.example.telecare.model.User;

public interface UserService {

    User registerPatient(User user);
    User registerDoctor(User user);
    void encodePassword(User user);
}
