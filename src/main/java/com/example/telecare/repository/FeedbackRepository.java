package com.example.telecare.repository;

import com.example.telecare.dto.FeedbackDTOInf;
import com.example.telecare.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FeedbackRepository extends JpaRepository<Feedback,Integer> {
    @Query(value = "SELECT a.id,u.full_name as fullName,u.image_url as imageUrl,f.comment,f.rating,f.updated_at as time FROM \n" +
            "telecare.feedback f\n" +
            "left outer join telecare.feedback_appointment fa on f.id = fa.feedback_id\n" +
            "left outer join telecare.appointment a on a.id = fa.apointment_id\n" +
            "left outer join telecare.patient p on p.patient_id = a.patient_id\n" +
            "left outer join telecare.user u on u.id = p.patient_id where a.doctor_id = ?1 and f.is_hidden = 1",
            nativeQuery = true)
    List<FeedbackDTOInf> getListFeedBackByDoctor(int uid);
}
