package com.example.telecare.service;

import com.example.telecare.dto.PaymentDTO;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.RequestBody;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

public interface PaymentService {
     ResponseEntity<?> returnPayment(String vnp_TmnCode,
                                           String vnp_Amount,
                                           String vnp_BankCode,
                                           String vnp_BankTranNo,
                                           String vnp_CardType,
                                           String vnp_PayDate,
                                           String vnp_OrderInfo,
                                           String vnp_TransactionNo,
                                           String vnp_ResponseCode,
                                           String vnp_TransactionStatus,
                                           String vnp_TxnRef,
                                           String vnp_SecureHashType,
                                           String vnp_SecureHash,
                                           HttpServletRequest request);

     void returnIpn(String vnp_TmnCode,
                    String vnp_Amount,
                    String vnp_BankCode,
                    String vnp_BankTranNo,
                    String vnp_CardType,
                    String vnp_PayDate,
                    String vnp_OrderInfo,
                    String vnp_TransactionNo,
                    String vnp_ResponseCode,
                    String vnp_TransactionStatus,
                    String vnp_TxnRef,
                    String vnp_SecureHashType,
                    String vnp_SecureHash,
                    HttpServletRequest request);

     ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO,
                                           HttpServletRequest req) throws UnsupportedEncodingException;

}
