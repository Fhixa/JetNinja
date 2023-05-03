package com.zenith.JetNinja.utils;

import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HttpRequest {
    private static final OkHttpClient client = new OkHttpClient();

    private final CookieScraper cookieScraper;
    private final CookieExtractor cookieExtractor;

    public HttpRequest(CookieScraper cookieScraper, CookieExtractor cookieExtractor) {
        this.cookieScraper = cookieScraper;
        this.cookieExtractor = cookieExtractor;
    }

    public Map<String, String> getRequest(String url , String ReqCookie) throws IOException {
        Map<String, String> responseMap = new HashMap<>();

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", ReqCookie)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            List<String> cookies = cookieScraper.getCookies(response);

            String stCookie = "";
            String jbCookie = "";

            if(cookies != null){
                stCookie = cookieExtractor.getCookieValue(cookies.get(0));
                jbCookie = cookieExtractor.getCookieValue(cookies.get(1));
            }

            responseMap.put("stCookie", stCookie);
            responseMap.put("jbCookie", jbCookie);
            responseMap.put("body", response.body().string());

            return responseMap;
        }

    }


    public Response postRequest(String url, String cookie, RequestBody body) throws  IOException{
        Request request = new Request.Builder()
                .url(url)
                .header("Cookie", cookie)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//            System.out.println(response.body());
            return response;
        }


    }
}