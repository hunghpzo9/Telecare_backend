package com.example.telecare.repository;

import com.example.telecare.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository  extends JpaRepository<Role,Integer> {
    @Query(value = "Select * from telecare.role where name = ?1",
            nativeQuery = true)
    Role findByName(String name);
}
