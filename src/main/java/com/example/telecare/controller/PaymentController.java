package com.example.telecare.controller;

import com.example.telecare.config.VnpayConfig;
import com.example.telecare.dto.PaymentDTO;
import com.google.gson.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/payment")
public class PaymentController {


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
        try {

	/*  IPN URL: Record payment results from VNPAY
	Implementation steps:
	Check checksum
	Find transactions (vnp_TxnRef) in the database (checkOrderId)
	Check the payment status of transactions before updating (checkOrderStatus)
	Check the amount (vnp_Amount) of transactions before updating (checkAmount)
	Update results to Database
	Return recorded results to VNPAY
	*/

            // ex:  	PaymnentStatus = 0; pending
            //              PaymnentStatus = 1; success
            //              PaymnentStatus = 2; Faile

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
            String signValue = VnpayConfig.hashAllFields(fields);
            if (signValue.equals(vnp_SecureHash)) {

                boolean checkOrderId = true; // vnp_TxnRef exists in your database
                boolean checkAmount = true; // vnp_Amount is valid (Check vnp_Amount VNPAY returns compared to the amount of the code (vnp_TxnRef) in the Your database).
                boolean checkOrderStatus = true; // PaymnentStatus = 0 (pending)


                if (checkOrderId) {
                    if (checkAmount) {
                        if (checkOrderStatus) {
                            if ("00".equals(request.getParameter("vnp_ResponseCode"))) {

                                //Here Code update PaymnentStatus = 1 into your Database
                            } else {

                                // Here Code update PaymnentStatus = 2 into your Database
                            }
                            System.out.println("{\"RspCode\":\"00\",\"Message\":\"Confirm Success\"}");
                        } else {

                            System.out.println("{\"RspCode\":\"02\",\"Message\":\"Order already confirmed\"}");
                        }
                    } else {
                        System.out.println("{\"RspCode\":\"04\",\"Message\":\"Invalid Amount\"}");
                    }
                } else {
                    System.out.println("{\"RspCode\":\"01\",\"Message\":\"Order not Found\"}");
                }
            } else {
                System.out.println("{\"RspCode\":\"97\",\"Message\":\"Invalid Checksum\"}");
            }
        } catch (Exception e) {
            System.out.println("{\"RspCode\":\"99\",\"Message\":\"Unknow error\"}");
        }
    }

    @GetMapping(value = "/returnPayment")
    public void returnPayment(
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

        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements(); ) {
            String fieldName = (String) params.nextElement();
            System.out.println(fieldName);
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



        if ("00".equals(request.getParameter("vnp_ResponseCode"))) {
            System.out.println("GD Thanh cong");
        } else {
            System.out.println("GD Khong thanh cong");
        }


    }

    @PostMapping(value = "/createPayment")
    public ResponseEntity<?> createPayment(@RequestBody PaymentDTO paymentDTO,
                                           HttpServletRequest req) throws UnsupportedEncodingException {

        String vnp_Version = VnpayConfig.vnp_Version;
        String vnp_Command = VnpayConfig.vnp_Command;
        String orderType = VnpayConfig.orderType;
        String vnp_TxnRef = VnpayConfig.getRandomNumber(20);
        String vnp_IpAddr = VnpayConfig.getIpAddress(req);
        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;
        String vnp_CurrCode = VnpayConfig.vnp_CurrCode;
        String vnp_OrderInfo = "Test vnpay";
        String vnp_Returnurl = VnpayConfig.vnp_Returnurl;

        int amount = paymentDTO.getAmount() * 100;

        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", vnp_CurrCode);
        if (paymentDTO.getBankCode() != null && !paymentDTO.getBankCode().isEmpty()) {
            vnp_Params.put("vnp_BankCode", VnpayConfig.vnp_BankCode);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
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
        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        String paymentUrl = VnpayConfig.vnp_PayUrl + "?" + queryUrl;
        HashMap<String, String> json = new HashMap<>();
        json.put("code", "00");
        json.put("message", "success");
        json.put("data", paymentUrl);
        return ResponseEntity.ok(json);
    }
}
