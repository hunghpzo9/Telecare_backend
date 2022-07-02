package com.example.telecare.repository;

import com.example.telecare.dto.DoctorAchievementDTO;
import com.example.telecare.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Integer> {
}
