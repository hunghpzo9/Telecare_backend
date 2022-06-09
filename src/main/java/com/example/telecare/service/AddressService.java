package com.example.telecare.service;

import com.example.telecare.model.Address;
import com.example.telecare.model.Patient;

import java.util.Optional;

public interface AddressService {
    Address findAddressById(int id);

}
