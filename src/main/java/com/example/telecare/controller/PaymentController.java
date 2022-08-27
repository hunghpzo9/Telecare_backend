package com.example.telecare.controller;

import com.example.telecare.dto.PaymentDTO;
import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.model.Payment;
import com.example.telecare.service.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {
    @Autowired
    PaymentServiceImpl paymentService;

    @GetMapping(value = "/returnIpn")
    public void returnIpn(
            @RequestParam(value = "vnp_TmnCode", required = false) String vnp_TmnCode,
            @RequestParam(value = "vnp_Amount", required = false) String vnp_Amount,
            @RequestParam(value = "vnp_BankCode", required = false) String vnp_BankCode,
            @RequestParam(value = "vnp_BankTranNo", required = false) String vnp_BankTranNo,
            @RequestParam(value = "vnp_CardType", required = false) String vnp_CardType,
            @RequestParam(value = "vnp_PayDate", required = false) String vnp_PayDate,
            @RequestParam(value = "vnp_OrderInfo", required = false) String vnp_OrderInfo,
            @RequestParam(value = "vnp_TransactionNo", required = false) String vnp_TransactionNo,
            @RequestParam(value = "vnp_ResponseCode", required = false) String vnp_ResponseCode,
            @RequestParam(value = "vnp_TransactionStatus", required = false) String vnp_TransactionStatus,
            @RequestParam(value = "vnp_TxnRef", required = false) String vnp_TxnRef,
            @RequestParam(value = "vnp_SecureHashType", required = false) String vnp_SecureHashType,
            @RequestParam(value = "vnp_SecureHash", required = false) String vnp_SecureHash,
            HttpServletRequest request) {
        paymentService.returnIpn(vnp_TmnCode,
                vnp_Amount,
                vnp_BankCode,
                vnp_BankTranNo,
                vnp_CardType,
                vnp_PayDate,
                vnp_OrderInfo,
                vnp_TransactionNo,
                vnp_ResponseCode,
                vnp_TransactionStatus,
                vnp_TxnRef,
                vnp_SecureHashType,
                vnp_SecureHash,
                request);
    }

    @GetMapping(value = "/returnPayment")
    public ResponseEntity<?> returnPayment(
            @RequestParam(value = "vnp_TmnCode", required = false) String vnp_TmnCode,
            @RequestParam(value = "vnp_Amount", required = false) String vnp_Amount,
            @RequestParam(value = "vnp_BankCode", required = false) String vnp_BankCode,
            @RequestParam(value = "vnp_BankTranNo", required = false) String vnp_BankTranNo,
            @RequestParam(value = "vnp_CardType", required = false) String vnp_CardType,
            @RequestParam(value = "vnp_PayDate", required = false) String vnp_PayDate,
            @RequestParam(value = "vnp_OrderInfo", required = false) String vnp_OrderInfo,
            @RequestParam(value = "vnp_TransactionNo", required = false) String vnp_TransactionNo,
            @RequestParam(value = "vnp_ResponseCode", required = false) String vnp_ResponseCode,
            @RequestParam(value = "vnp_TransactionStatus", required = false) String vnp_TransactionStatus,
            @RequestParam(value = "vnp_TxnRef", required = false) String vnp_TxnRef,
            @RequestParam(value = "vnp_SecureHashType", required = false) String vnp_SecureHashType,
            @RequestParam(value = "vnp_SecureHash", required = false) String vnp_SecureHash,
            HttpServletRequest request) {

        return paymentService.returnPayment(vnp_TmnCode,
                vnp_Amount,
                vnp_BankCode,
                vnp_BankTranNo,
                vnp_CardType,
                vnp_PayDate,
                vnp_OrderInfo,
                vnp_TransactionNo,
                vnp_ResponseCode,
                vnp_TransactionStatus,
                vnp_TxnRef,
                vnp_SecureHashType,
                vnp_SecureHash,
                request);

    }

    @PostMapping(value = "/createPayment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO,
                                           HttpServletRequest req) throws UnsupportedEncodingException {
        return paymentService.createPayment(paymentDTO, req);
    }

    @GetMapping(value = "/detail")
    public ResponseEntity<?> paymentDetail(@RequestParam(value = "appointment_id") int appointment_id) {
        Payment payment = paymentService.findSuccessPaymentDetailByAppointmentId(appointment_id);
        return ResponseEntity.ok(payment);
    }

    @PostMapping(value = "/changeStatusAllCancelPayment")
    public ResponseEntity<?> changeStatusAllCancelPayment(@RequestParam(value = "patientId")
                                                                  int patientId) {
        paymentService.changeStatusAllCancelPayment(patientId);
        return ResponseEntity.ok(new ResponseOkMessage("Đã huỷ giao dịch thành công",new Date()));
    }
}
