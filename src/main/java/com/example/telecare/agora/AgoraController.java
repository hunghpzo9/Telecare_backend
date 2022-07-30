package com.example.telecare.agora;

import com.example.telecare.agora.media.RtcTokenBuilder;
import com.example.telecare.agora.rtm.RtmTokenBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(maxAge = 60 * 60 * 24 * 30)
@RestController
@RequestMapping("/api/v1/agora")

public class AgoraController {

    @PostMapping(value = "/rtc")
    public Object getRTCToken(@RequestBody AgoraRepository resource) throws JSONException {

        RtcTokenBuilder token = new RtcTokenBuilder();
        String channelName = resource.getChannelName();
        int expireTime = resource.getExpirationTimeInSeconds();
        RtcTokenBuilder.Role role = RtcTokenBuilder.Role.Role_Subscriber;
        int uid = resource.getUid();

        // check for null channelName
        if (channelName == null) {
            JSONObject error = new JSONObject();
            error.put("error", "Channel Name cannot be blank");
            return error.getString("error");
        }

        if (expireTime == 0) {
            expireTime = 3600;
        }

        if (resource.getRole() == 1) {
            role = RtcTokenBuilder.Role.Role_Publisher;
        } else if (resource.getRole() == 0) {
            role = RtcTokenBuilder.Role.Role_Attendee;
        }
        int timestamp = (int) (System.currentTimeMillis() / 1000 + expireTime);


        String result = token.buildTokenWithUid(resource.appId, resource.appCertificate,
                channelName, uid, role, timestamp);
        JSONObject jsondict = new JSONObject();
        jsondict.put("rtcToken", result);
        return jsondict.toString();

    }

    @PostMapping(value = "/rtm")
    public Object getRTMToken(@RequestBody AgoraRTMRepository resource) throws Exception {

        String userId = resource.getUserId();

        if (userId == null) {
            JSONObject error = new JSONObject();
            error.put("error", "User ID cannot be blank");
            return error.getString("error");
        }

        RtmTokenBuilder token = new RtmTokenBuilder();
        String result = token.buildToken(resource.getAppId(), resource.getAppCertificate(), userId, RtmTokenBuilder.Role.Rtm_User, resource.getExpireTimestamp());
        System.out.println(result);
        JSONObject jsondict = new JSONObject();
        jsondict.put("rtmToken", result);
        return jsondict.toString();

    }
}
