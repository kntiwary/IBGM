package com.goalsr.kidsgrowth.kidsgrowthcharts.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.Base64Utils;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by bhanagandi on 10/9/2015.
 */
public class RestUtils {
    public static HttpHeaders getHeaders(String auth, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.set("token", token);
        if (auth != null) {
            String basic = new String(Base64Utils.encode(auth.getBytes(Charset
                    .forName("UTF-8"))));
            headers.set("Authorization", "Basic " + basic);
        }
        return headers;
    }
}

