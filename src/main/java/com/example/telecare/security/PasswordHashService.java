package com.example.telecare.security;

import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

@Service
public class PasswordHashService {
    private static final String SECRET_KEY = "TELECARE_CAPSTONE_SECRET";
    private byte[] keyValue;
    private static SecretKeySpec secretKey;

    class AES {
        public String encrypt(String data) {
            try {
                setKey(SECRET_KEY);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes("UTF-8")));
            } catch (Exception e) {
                System.out.println("Error while encrypting: " + e);
            }
            return null;
        }

        public String decrypt(String data) {
            try {
                setKey(SECRET_KEY);
                Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
                return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
            } catch (Exception e) {
                System.out.println("Error while encrypting: " + e);
            }
            return null;
        }

        private void setKey(final String myKey) {
            MessageDigest sha;
            try {
                keyValue = myKey.getBytes("UTF-8");
                sha = MessageDigest.getInstance("SHA-1");
                keyValue = sha.digest(keyValue);
                keyValue = Arrays.copyOf(keyValue, 16);
                secretKey = new SecretKeySpec(keyValue, "AES");
            } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }



    public String encodePasswordAlgorithm(String salt,String password) {
        AES aes = new AES();
        String encodePass = null;
        try {
            encodePass = aes.encrypt(salt+password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodePass;
    }

    public String decodePasswordAlgorithm(String password) {
        AES aes = new AES();
        String encodePass = null;
        try {
            encodePass = aes.decrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encodePass;
    }
}


