package com.example.telecare.agora;

public class Agora {
    static String appId = "97cc52a260b9460383581e0bd93ebb9b"; //replace app id
    static String appCertificate = "9a611a40ceca450385947750adb665cc"; //replace app cert
    private String channelName;
    private int uid = 0;
    private int expirationTimeInSeconds = 3600;
    private int role = 2; // By default subscriber

    public static String getAppId() {
        return appId;
    }

    public static void setAppId(String appId) {
        Agora.appId = appId;
    }

    public static String getAppCertificate() {
        return appCertificate;
    }

    public static void setAppCertificate(String appCertificate) {
        Agora.appCertificate = appCertificate;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getExpirationTimeInSeconds() {
        return expirationTimeInSeconds;
    }

    public void setExpirationTimeInSeconds(int expirationTimeInSeconds) {
        this.expirationTimeInSeconds = expirationTimeInSeconds;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }


}