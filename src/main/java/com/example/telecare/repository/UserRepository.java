package com.example.telecare.repository;

import com.example.telecare.dto.interfaces.AdminDTOInf;
import com.example.telecare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query(value = "SELECT * FROM telecare.user WHERE phone = ?1",
            nativeQuery = true)
    User findUserByPhone(String phone);

    @Query(value = "SELECT * FROM telecare.user WHERE email = ?1",
            nativeQuery = true)
    User findUserByEmail(String email);
    @Query(value = "SELECT * FROM telecare.user WHERE id = ?1",
            nativeQuery = true)
    User findUserById(String uid);
    @Query(value = "SELECT u.id as id,u.full_name as fullName,u.date_of_birth as dob,u.gender as gender,u.phone as phone,u.email as email FROM telecare.user as u where id=?1",
            nativeQuery = true)
    AdminDTOInf findAdminById(int id);


}
