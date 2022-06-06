package com.example.telecare.repository;


import com.example.telecare.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Integer>  {
//    @Query(value = "INSERT INTO telecare.user_role VALUES(?1,?2)",
//            nativeQuery = true)
//    void setRole(int uid,int rid);
}
