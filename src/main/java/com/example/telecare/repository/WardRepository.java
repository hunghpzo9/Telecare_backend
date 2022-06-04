package com.example.telecare.repository;

import com.example.telecare.model.District;
import com.example.telecare.model.Ward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WardRepository extends JpaRepository<Ward,Integer> {
    @Query(value = "SELECT * FROM telecare.ward where districtid= ?1",
            nativeQuery = true)
    List<Ward> findWardById(String districtId);
}
