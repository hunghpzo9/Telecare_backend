package com.example.telecare.agora;

public class AgoraRTMRepository {
    private static String appId = "054668277fe54f01b8a02a9fe6ad5260";
    private static String appCertificate = "1a55859009ee485bbe395e844e2b5afa";
    private String userId = "0";
    private int expireTimestamp = 0;

    public static String getAppId() {
        return appId;
    }

    public static String getAppCertificate() {
        return appCertificate;
    }

    public static void setAppCertificate(String appCertificate) {
        AgoraRTMRepository.appCertificate = appCertificate;
    }

    public String getUserId() {
        return userId;
    }


    public int getExpireTimestamp() {
        return expireTimestamp;
    }

    public static void setAppId(String appId) {
        AgoraRTMRepository.appId = appId;
    }
}