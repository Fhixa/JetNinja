package com.zenith.JetNinja.utils;

import okhttp3.*;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

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

    public Response getRequestWithCookies(String url, List<String> cookies) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", cookies.get(0) + ";" + cookies.get(1))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            return response;
        }

    }

    public String getRequestWBody(String url) throws IOException {
        // Build the GET request using the provided URL
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Execute the request and retrieve the response
        try (Response response = client.newCall(request).execute()) {
            // If the response is not successful, throw an IOException
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);


            return response.body().string();
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