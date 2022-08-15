package com.example.telecare.repository;

import com.example.telecare.model.ListedPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ListedPriceRepository extends JpaRepository<ListedPrice,Integer> {
    @Query(value = "SELECT * FROM telecare.listed_price where is_use = 0 order by created_at desc LIMIT 1",
            nativeQuery = true)
    ListedPrice getInUseListedPrice();
}
