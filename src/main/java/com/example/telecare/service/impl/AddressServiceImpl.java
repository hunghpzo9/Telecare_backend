package com.example.telecare.service.impl;

import com.example.telecare.exception.ResourceNotFoundException;
import com.example.telecare.model.Address;
import com.example.telecare.repository.AddressRepository;
import com.example.telecare.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressRepository addressRepository;
    @Override
    public Address findAddressById(int id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Not found address"));
    }

}
