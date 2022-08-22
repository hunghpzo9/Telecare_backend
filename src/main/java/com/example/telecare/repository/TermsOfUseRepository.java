package com.example.telecare.repository;

import com.example.telecare.model.TermsOfUse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TermsOfUseRepository extends JpaRepository<TermsOfUse,Integer> {
    @Query(value = "SELECT url FROM telecare.terms_of_use where is_use = 0;",nativeQuery = true)
    String getTermsOfUseUrl();
}
