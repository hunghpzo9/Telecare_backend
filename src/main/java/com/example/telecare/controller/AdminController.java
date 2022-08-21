package com.example.telecare.controller;

import com.example.telecare.dto.AuthenticationRequest;
import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.dto.interfaces.*;
import com.example.telecare.model.Feedback;
import com.example.telecare.model.ListedPrice;
import com.example.telecare.model.Medicine;
import com.example.telecare.model.Payment;
import com.example.telecare.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;

    @GetMapping("/medicine/getAll")
    public ResponseEntity<?> getAllMedicine(@RequestParam int index, @RequestParam String searchText) {
        List<Medicine> medicines = adminService.getAllMedicine(index, searchText);
        if (medicines.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity(medicines, HttpStatus.OK);
    }

    @GetMapping("/medicine/numberOfMedicine")
    public ResponseEntity<?> getNumberOfMedicine(@RequestParam String searchText) {
        int numberOfMedicine = adminService.getNumberOfMedicine(searchText);

        return new ResponseEntity(numberOfMedicine, HttpStatus.OK);
    }

    @PutMapping(value = "/medicine/updateStatus")
    public ResponseEntity<?> updateMedicineStatusForAdmin(@RequestParam int id,@RequestParam Byte status){
        adminService.updateMedicineStatus(id,status);
        return ResponseEntity.ok(new ResponseOkMessage("Cập nhật thành công", new Date()));
    }

    @GetMapping(value = "/doctor/{id}")
    public DoctorDTOInf findDoctorDetail(@PathVariable int id) {
        return adminService.findDoctorById(id);
    }

    @GetMapping(value = "/patient")
    public PatientDTOAdminInf findPatientForAdmin(@RequestParam int id) {
        return adminService.findPatientByIdForAdmin(id);
    }

    @GetMapping(value = "/doctor")
    public ResponseEntity<List<DoctorDTOInf>> getAllDoctor(@RequestParam int index, @RequestParam String searchText) {
        return new ResponseEntity<>(adminService.getAllDoctor(index, searchText), HttpStatus.OK);
    }

    @GetMapping("/doctor/numberOfDoctor")
    public ResponseEntity<Integer> getNumberOfDoctor(@RequestParam String searchText) {
        int medicines = adminService.getNumberOfDoctor(searchText);
        return new ResponseEntity<Integer>(medicines, HttpStatus.OK);

    }

    @GetMapping(value = "/appointment/getAll")
    public ResponseEntity<List<AppointmentDTOInfForAdmin>> getAllAppointmentForAdmin(@RequestParam int index, @RequestParam String searchText) {
        return new ResponseEntity<>(adminService.getAllAppointmentForAdmin(index, searchText), HttpStatus.OK);
    }

    @GetMapping("/appointment/numberOfAppointment")
    public ResponseEntity<Integer> getNumberOfAppointment(@RequestParam String searchText) {
        int noa = adminService.getNumberOfAppointmentForAdmin(searchText);
        return new ResponseEntity(noa, HttpStatus.OK);

    }

    @GetMapping("/appointment/detail/{id}")
    public ResponseEntity<AppointmentDTOInfForAdmin> getNumberOfPayment(@PathVariable int id) {
        return new ResponseEntity(adminService.getAppointmentDetailForAdmin(id), HttpStatus.OK);

    }

    @GetMapping("/patient/numberOfPatient")
    public ResponseEntity<Integer> getNumberOfPatient(@RequestParam String searchText) {
        int medicines = adminService.getNumberOfPatient(searchText);
        return new ResponseEntity(medicines, HttpStatus.OK);

    }

    @PostMapping("/auth/loginForAdmin")
    public ResponseEntity<?> loginForAdmin(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return adminService.loginForAdmin(authenticationRequest);
    }

    @PutMapping(value = "/user/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestParam Byte isActive, @RequestParam int id, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam Date expireDate, @RequestParam String reason) throws ParseException {

        adminService.updateStatus(isActive, id, expireDate, reason);
        return ResponseEntity.ok(new ResponseOkMessage("Cập nhật trạng thái thành công", new Date()));

    }

    @PutMapping(value = "/user/updateStatusForPatient")
    public ResponseEntity<?> updateStatusForPatient(@RequestParam Byte isActive, @RequestParam int id, @RequestParam String reason) throws ParseException {

        adminService.updateStatusForPatient(isActive, id, reason);
        return ResponseEntity.ok(new ResponseOkMessage("Cập nhật thành công", new Date()));

    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<AdminDTOInf> findAdminById(@PathVariable("id") int id) {
        AdminDTOInf admin = adminService.findAdminById(id);
        return new ResponseEntity<AdminDTOInf>(admin, HttpStatus.OK);

    }

    @PutMapping("/auth/changePassword")
    public ResponseEntity<?> changeOldPassword(@RequestParam("id") String id,
                                               @RequestParam("password") String password,
                                               @RequestParam("newPassword") String newPassword) {
        return adminService.changeOldPassword(id, password, newPassword);
    }

    @PostMapping(value = "/doctor/sendNotificationFromAdmin")
    public ResponseEntity<?> sendNotificationFromAdmin(@RequestParam int id, @RequestParam String content) {
        adminService.sendNotification(id, "Thông báo từ admin: " + content);
        return ResponseEntity.ok(new ResponseOkMessage("Gửi thông báo thành công", new Date()));

    }

    @GetMapping(value = "/patient/getAll")
    public ResponseEntity<List<PatientDTOAdminInf>> getAllPatient(@RequestParam int index, @RequestParam String searchText) {
        return new ResponseEntity<>(adminService.getAllPatient(index, searchText), HttpStatus.OK);
    }

    @GetMapping(value = "/payment/getAll")
    public ResponseEntity<List<Payment>> getAllPayment(@RequestParam int index, @RequestParam String searchText) {
        return new ResponseEntity<>(adminService.getAllPayment(index, searchText), HttpStatus.OK);
    }

    @GetMapping("/payment/numberOfPayment")
    public ResponseEntity<Integer> getNumberOfPayment(@RequestParam String searchText) {
        int nop = adminService.getNumberOfPayment(searchText);
        return new ResponseEntity<>(nop, HttpStatus.OK);

    }

    @GetMapping(value = "/report/getAll")
    public ResponseEntity<List<ReportDTOInfForAdmin>> getAllReport(@RequestParam int index, @RequestParam String searchText) {
        return new ResponseEntity<>(adminService.getListReportForAdmin(index, searchText), HttpStatus.OK);
    }

    @GetMapping("/report/numberOfReport")
    public ResponseEntity<Integer> getNumberOfReport(@RequestParam String searchText) {
        int nor = adminService.getNumberOfReportForAdmin(searchText);
        return new ResponseEntity<Integer>(nor, HttpStatus.OK);

    }

    @PutMapping(value = "/report/updateStatus")
    public ResponseEntity<?> updateStatusForReport(@RequestParam int reportId, @RequestParam int statusId) {

        adminService.updateStatusForReport(reportId, statusId);
        return ResponseEntity.ok(new ResponseOkMessage("Cập nhật thành công", new Date()));

    }

    @GetMapping(value = "/appointmentDetails/getAll")
    public ResponseEntity<List<AppointmentDTOInfForAdmin>> getAllAppointmentDetailsForAdmin(@RequestParam int index, @RequestParam String searchText) {
        return new ResponseEntity<>(adminService.getAllAppointmentDetailsForAdmin(index, searchText), HttpStatus.OK);
    }

    @GetMapping("/appointmentDetails/numberOfAppointment")
    public ResponseEntity<Integer> getNumberOfAppointmentDetails(@RequestParam String searchText) {
        int noa = adminService.getNumberOfAppointmentDetailsForAdmin(searchText);
        return new ResponseEntity(noa, HttpStatus.OK);

    }

    @GetMapping(value = "/feedback/appointmentId={id}")
    public Feedback getFeedbackByAppointmentId(@PathVariable int id) {
        return adminService.findFeedBackByAppointmentId(id);
    }
    @PutMapping(value = "/feedback/updateStatus")
    public ResponseEntity<?> updateFeedbackStatusForAdmin( @RequestParam int id,@RequestParam Byte status) throws ParseException {

        adminService.updateFeedbackStatusForAdmin(id,status);
        return ResponseEntity.ok(new ResponseOkMessage("Cập nhật thành công", new Date()));
    }

    @PostMapping(value = "/notification/sendAll")
    public ResponseEntity<?> sendNotificationToAllUser(@RequestParam String role,
                                                       @RequestParam int money ,
                                                       @RequestParam String message) {
        adminService.sendNotificationToAllUser(role, money, message);
        return ResponseEntity.ok(new ResponseOkMessage("Đã gửi thông báo thành công", new Date()));
    }
    @GetMapping(value = "/listedPrice/getAll")
    public ResponseEntity<List<ListedPrice>> getAllListedPriceForAdmin(@RequestParam int index, @RequestParam String searchText) {
        return new ResponseEntity<>(adminService.getAllListedPriceForAdmin(index, searchText), HttpStatus.OK);
    }

    @GetMapping("/listedPrice/numberOfListedPrice")
    public ResponseEntity<Integer> getNumberOfListedPrice(@RequestParam String searchText) {
        int noa = adminService.getNumberOfListedPrice(searchText);
        return new ResponseEntity(noa, HttpStatus.OK);

    }
    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboard() {
        var dashboard = adminService.getDashboard();
        return new ResponseEntity(dashboard, HttpStatus.OK);

    }

}
