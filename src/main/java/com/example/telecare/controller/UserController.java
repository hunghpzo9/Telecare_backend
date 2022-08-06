package com.example.telecare.controller;

import com.example.telecare.dto.interfaces.AdminDTOInf;
import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    UserServiceImpl userService;

    @PutMapping(value = "/updateStatus")
    public ResponseEntity<?> updateStatus(@RequestParam Byte isActive, @RequestParam int id,@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @RequestParam Date expireDate,@RequestParam String reason) throws ParseException {

        userService.updateStatus(isActive,id,expireDate,reason);
        return ResponseEntity.ok(new ResponseOkMessage("Cập nhật trạng thái thành công", new Date()));

    }
    @PutMapping(value = "/updateStatusForPatient")
    public ResponseEntity<?> updateStatusForPatient(@RequestParam Byte isActive, @RequestParam int id,@RequestParam String reason) throws ParseException {

        userService.updateStatusForPatient(isActive,id,reason);
        return ResponseEntity.ok(new ResponseOkMessage("Cập nhật thành công", new Date()));

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AdminDTOInf> findUserById(@PathVariable("id") int id) {
        AdminDTOInf admin =  userService.findAdminById(id);
        return new ResponseEntity<AdminDTOInf>(admin, HttpStatus.OK);

    }
}
