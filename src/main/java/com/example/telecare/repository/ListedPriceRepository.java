package com.example.telecare.repository;

import com.example.telecare.model.ListedPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ListedPriceRepository extends JpaRepository<ListedPrice,Integer> {
    @Query(value = "SELECT * FROM telecare.listed_price where is_use = 0 order by created_at desc LIMIT 1",
            nativeQuery = true)
    ListedPrice getInUseListedPrice();
    @Query(value = "SELECT * FROM telecare.listed_price lp\n" +
            "       where lp.id like %?2% or lp.created_at like %?2% or lp.reason like %?2%\n" +
            "       order by lp.created_at desc\n" +
            "       limit ?1,10",
            nativeQuery = true)
    List<ListedPrice> getAllListedPriceForAdmin(int index, String search);
    @Query(value = "SELECT count(*) FROM telecare.listed_price lp\n" +
            "where lp.id like %?1% or lp.created_at like %?1% or lp.reason like %?1%"
            , nativeQuery = true)
    int getNumberOfListedPrice(String search);

}
