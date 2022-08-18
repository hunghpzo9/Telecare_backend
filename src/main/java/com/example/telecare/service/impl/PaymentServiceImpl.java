package com.example.telecare.service.impl;

import com.example.telecare.config.VnpayConfig;
import com.example.telecare.dto.PaymentDTO;
import com.example.telecare.dto.ResponseOkMessage;
import com.example.telecare.enums.PaymentStatus;
import com.example.telecare.exception.NotFoundException;
import com.example.telecare.model.Appointment;
import com.example.telecare.model.Payment;
import com.example.telecare.repository.AppointmentRepository;
import com.example.telecare.repository.PaymentRepository;
import com.example.telecare.service.PaymentService;
import com.example.telecare.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    VnpayConfig vnpayConfig;
    @Autowired
    PaymentRepository paymentRepository;
    @Autowired
    AppointmentRepository appointmentRepository;

    private final int PaymentStatusPending = 0;
    private final int PaymentStatusSuccess = 1;
    private final int PaymentStatusFailed = 2;
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Override
    public ResponseEntity<?> returnPayment(String vnp_TmnCode,
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
                                           HttpServletRequest request) {
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = (String) params.nextElement();
            String fieldValue = request.getParameter(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }

        String signValue = vnpayConfig.hashAllFields(fields);
        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
                return ResponseEntity.ok(new ResponseOkMessage(Constants.PAYMENT_SUCCESS_MESSAGE, new Date()));
            } else {
                return ResponseEntity.ok(new ResponseOkMessage(Constants.PAYMENT_FAILED_MESSAGE, new Date()));
            }

        } else {
            return ResponseEntity.ok(new ResponseOkMessage("Chu ky khong hop le", new Date()));

        }
    }

    @Override
    public void returnIpn(String vnp_TmnCode,
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
                          HttpServletRequest request) {
        try {


            //Begin process return from VNPAY
            Map fields = new HashMap();
            for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
                String fieldName = (String) params.nextElement();
                String fieldValue = request.getParameter(fieldName);
                if ((fieldValue != null) && (fieldValue.length() > 0)) {
                    fields.put(fieldName, fieldValue);
                }
            }

            if (fields.containsKey("vnp_SecureHashType")) {
                fields.remove("vnp_SecureHashType");
            }
            if (fields.containsKey("vnp_SecureHash")) {
                fields.remove("vnp_SecureHash");
            }

            // Check checksum
            String signValue = vnpayConfig.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {

                boolean checkOrderId = true; // vnp_TxnRef exists in your database
                boolean checkAmount = true; // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the Your database).
                boolean checkOrderStatus = true; // PaymentStatus = 0 (pending)

                Payment payment = paymentRepository.findPaymentByTrace(vnp_TxnRef);
                if (payment == null) {
                    checkOrderId = false;
                }
                int amount = Integer.valueOf(vnp_Amount)/100;
                if (checkOrderId) {
                    if (!String.valueOf(amount).equals(payment.getAmount())) {
                        checkAmount = false;
                    }
                    if (checkAmount) {
                        if (payment.getStatus() != PaymentStatusPending) {
                            checkOrderStatus = false;
                        }
                        if (checkOrderStatus) {
                            payment.setBanktranNo(vnp_BankTranNo);
                            payment.setCardtype(vnp_CardType);
                            payment.setTransactionNo(vnp_TransactionNo);
                            if ("00".equals(vnp_ResponseCode)) {
                                Appointment appointment = appointmentRepository.findById(payment.getAppointmentId())
                                        .orElseThrow(() -> new NotFoundException("Not found schedule"));
                                appointment.setPaymentStatusId(PaymentStatus.PAID.status);
                                payment.setStatus(PaymentStatusSuccess);
                                appointmentRepository.save(appointment);
                                //Here Code update PaymnentStatus = 1 into your Database
                            } else {
                                payment.setStatus(PaymentStatusFailed);
                                // Here Code update PaymnentStatus = 2 into your Database
                            }
                            payment.setResponseCode(vnp_ResponseCode);
                            paymentRepository.save(payment);
                            logger.info("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
                        } else {
                            logger.info("{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}");

                        }
                    } else {
                        logger.info("{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}");

                    }
                } else {
                    logger.info("{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}");

                }
            } else {
                logger.info("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");

            }
        } catch (Exception e) {
            logger.info("{\"RspCode\":\"99\",\"Message\":\"Unknow error\"}");
        }
    }

    @Override
    public ResponseEntity<?> createPayment(PaymentDTO paymentDTO, HttpServletRequest req) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = vnpayConfig.orderType;
        Payment existedPayment;
        String vnp_TxnRef;
        do{
            String trace = VnpayConfig.getRandomNumber(20);
            existedPayment = paymentRepository.findPaymentByTrace(trace);
            vnp_TxnRef = trace;
        }while(existedPayment != null);


        String vnp_IpAddr = VnpayConfig.getIpAddress(req);
        String vnp_TmnCode = vnpayConfig.vnpTmnCode;

        int amount = paymentDTO.getAmount() * 100;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_BankCode", paymentDTO.getBankCode());
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnpayConfig.vnpOrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnpayConfig.vnpReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        TimeZone tz = TimeZone.getTimeZone("Asia/Ho_Chi_Minh");
        Calendar cld = Calendar.getInstance();
        cld.setTimeZone(tz);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);
        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = vnpayConfig.hmacSHA512(vnpayConfig.vnpHashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = vnpayConfig.vnpPayUrl + "?" + queryUrl;
        HashMap<String, String> json = new HashMap<>();
        json.put("code", "00");
        json.put("message", "success");
        json.put("data", paymentUrl);

        //save pending payment to database

        SimpleDateFormat databaseFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateDatabase = databaseFormat.format(cld.getTime());

        Payment payment = new Payment();
        payment.setStatus(PaymentStatusPending);
        payment.setAppointmentId(paymentDTO.getAppointmentId());
        payment.setAmount(String.valueOf(paymentDTO.getAmount()));
        payment.setBankcode(paymentDTO.getBankCode());
        payment.setDescription(paymentDTO.getDescription());
        payment.setTransactionDate(dateDatabase);
        payment.setTrace(vnp_TxnRef);
        payment.setInstallment("No");
        paymentRepository.save(payment);

        return ResponseEntity.ok(json);
    }

    @Override
    public Payment findSuccessPaymentDetailByAppointmentId(int id) {
        return paymentRepository.findPaymentDetailByAppointmentId(id,PaymentStatusSuccess);
    }

    @Override
    public List<Payment> getAllPayment(int index, String searchText) {
        return paymentRepository.getAllPayment(index, searchText);
    }

    @Override
    public int getNumberOfPayment(String searchText) {
        return paymentRepository.getNumberOfPayment(searchText);
    }
}
