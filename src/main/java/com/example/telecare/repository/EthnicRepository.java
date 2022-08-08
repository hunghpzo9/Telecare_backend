package com.example.telecare.repository;

import com.example.telecare.model.Ethnic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EthnicRepository extends JpaRepository<Ethnic,Integer> {
    @Query(value = "SELECT u.id,u.full_name,d.job_place,d.position,s.name FROM telecare.doctor d \n" +
            "left outer join telecare.user u on d.doctor_id = u.id\n" +
            "left outer join telecare.doctor_specialty ds on d.doctor_id = ds.doctor_id\n" +
            "left outer join telecare.specialty s on ds.specialty_id = s.id\n" +
            "where u.full_name like ?1 or s.name like ?1 or d.job_place like ?1 group by u.id\n",
            nativeQuery = true)
    void searchDoctor(String searchText);
}
