package com.scm.arjun.scm20.services;

import com.scm.arjun.scm20.DTO.RecaptchaResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchService {

    @Value("${google.recaptcha.secret}")
    private String reCaptchSecret;

    public static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
    private final RestTemplate restTemplate;

    public CaptchService(RestTemplate restTemplate) {  //Injecting RestTemplate Bean is more efficient
        this.restTemplate = restTemplate;
    }

    public boolean verifyCaptcha(String captchaResponse) {
        MultiValueMap<String, String> requestMap = new LinkedMultiValueMap<>(); // Unique Key for List<Value> : MultiValueMap
        requestMap.add("secret", reCaptchSecret);
        requestMap.add("response", captchaResponse);

        try {
            RecaptchaResponse response = restTemplate.postForObject(GOOGLE_RECAPTCHA_VERIFY_URL, requestMap, RecaptchaResponse.class);
            return response != null && response.isSuccess();
        } catch (Exception e) {
            System.out.println("Exception in CaptchaService:: " + e.getMessage());
            return false;
        }
    }
}
