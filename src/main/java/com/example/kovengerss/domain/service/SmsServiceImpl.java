package com.example.kovengerss.domain.service;

import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.util.HashMap;
@Service
public class SmsServiceImpl implements SmsService {
    @Override
    public void sendMessage(String authCode, String userPhoneNum) {
        String apiKey = "NCSGDKSNDYTMCT5V";
        String apiSecret = "XHPDA2WDFAT5S3JGDF1A4SROWRWZXD5E";
        String fromNumber = "01036758324";

        Message coolsms = new Message(apiKey, apiSecret);

        HashMap<String, String> params = new HashMap<>();
        params.put("to", userPhoneNum);
        params.put("from", fromNumber);
        params.put("type", "sms");
        params.put("text", "[SumSum] 인증번호 "+authCode+" 를 입력하세요.");
        params.put("app_version", "test app 1.2"); // application name and version

        try {
            JSONObject obj = coolsms.send(params);
            System.out.println(obj.toString());
        } catch (CoolsmsException e) {
            System.out.println(e.getMessage());
            System.out.println(e.getCode());
        }
    }
}
