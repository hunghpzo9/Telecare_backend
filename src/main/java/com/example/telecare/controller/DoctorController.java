package com.example.telecare.controller;

import com.example.telecare.dto.DoctorUpdateDTO;
import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.dto.interfaces.DoctorDTOInf;
import com.example.telecare.service.impl.DoctorServiceImpl;
import com.example.telecare.service.impl.NotificationServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/doctor")
public class DoctorController {
    @Autowired
    DoctorServiceImpl doctorService;
    @Autowired
    NotificationServiceImpl notificationService;

    @GetMapping(value = "/{id}")
    public DoctorDTOInf findDoctorDetail(@PathVariable int id) {
        return doctorService.findDoctorById(id);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<DoctorUpdateDTO> updateDoctor(@PathVariable("id") int id, @RequestBody DoctorUpdateDTO doctorDetail) {
        doctorService.updateDoctor(doctorDetail ,id);
        return ResponseEntity.ok(doctorDetail);
    }

    @GetMapping(value = "/search={search}/pageNo={pageNo}")
    public List<DoctorDTOInf> findDoctor(@PathVariable String search, @PathVariable int pageNo) {
        return doctorService.listAllDoctor("%" + search + "%", pageNo);
    }

    @GetMapping(value = "/searchBySpecialty/search={search}/pageNo={pageNo}")
    public List<DoctorDTOInf> findDoctorBySpecialty(@PathVariable String search, @RequestParam("specialtyId") List<Integer> specialtyId, @PathVariable int pageNo) {
        return doctorService.listAllDoctorBySpecialty("%" + search + "%", specialtyId, pageNo);
    }

    @DeleteMapping(value = "/removeFavoriteDoctor")
    public ResponseEntity<?> removeFavoriteDoctor(@RequestParam("patientId") int patientId, @RequestParam("doctorId") int doctorId) {
        doctorService.removeFavoriteDoctor(patientId,doctorId);
        return ResponseEntity.ok(new ResponseOkMessage("Remove successfully",new Date()));
    }
    @PostMapping(value = "/addFavoriteDoctor")
    public ResponseEntity<?> addFavoriteDoctor(@RequestParam("patientId") int patientId, @RequestParam("doctorId") int doctorId) {
        doctorService.addFavoriteDoctor(patientId,doctorId);
        return ResponseEntity.ok(new ResponseOkMessage("Add successfully",new Date()));
    }

    @GetMapping(value = "/searchFavorite/search={search}/pageNo={pageNo}")
    public List<DoctorDTOInf> findAllFavoriteDoctor(@PathVariable String search, @RequestParam("patientId") int patientId, @PathVariable int pageNo) {
        return doctorService.listAllFavoriteDoctorById("%" + search + "%", pageNo,patientId);
    }

    @GetMapping(value = "/isFavorite")
    public Boolean isFavoriteDoctor(@RequestParam("patientId") int patientId,
                                    @RequestParam("doctorId") int doctorId) {
        return doctorService.isFavoriteDoctor(patientId,doctorId);
    }

    @PutMapping(value = "/doctorId={id}/specialtyId={sId}")
    public ResponseEntity<?> addDoctorSpecialty(@PathVariable("id") int docId, @PathVariable("sId") int specialtyId) {
        doctorService.addDoctorSpecialty(docId ,specialtyId);
        return ResponseEntity.ok(new ResponseOkMessage("Add successful", new Date()));
    }
    @GetMapping(value = "")
    public ResponseEntity<List<DoctorDTOInf>> getAllDoctor(@RequestParam int index,@RequestParam String searchText) {
        return new ResponseEntity<>(doctorService.getAllDoctor(index,searchText), HttpStatus.OK);
    }
    @GetMapping("/numberOfDoctor")
    public ResponseEntity<Integer> getNumberOfDoctor(@RequestParam String searchText) {
        int medicines = doctorService.getNumberOfDoctor(searchText);
        return new ResponseEntity<Integer>(medicines, HttpStatus.OK);

    }
    @PostMapping(value = "/sendNotificationFromAdmin")
    public ResponseEntity<?> sendNotificationFromAdmin(@RequestParam int id, @RequestParam String content) {
        notificationService.sendNotification(id,"Thông báo từ admin: "+content);
        return ResponseEntity.ok(new ResponseOkMessage("Gửi thông báo thành công", new Date()));

    }

}
