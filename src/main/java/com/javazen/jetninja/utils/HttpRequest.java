package com.javazen.jetninja.utils;

import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class HttpRequest {
    private static final OkHttpClient client = new OkHttpClient();

    public Response getRequest(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return response;
        }

    }
    public Response postRequest(String url, String cookie) throws  IOException{
        RequestBody body = new FormBody.Builder()
                .add("email", "terah14907@in2reach.com")
                .build();

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