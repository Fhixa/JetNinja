package com.zenith.JetNinja;

import com.zenith.JetNinja.utils.HttpRequest;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class is responsible for sending verification emails to a given email address using a third-party HTTP request library.
 * It uses the OkHttp library for creating HTTP requests.
 *
 * The class is marked as a Spring Component so that it can be easily injected into other Spring-managed beans.
 */
@Component
public class SendVerificationEmail {

    /**
     * The HttpRequest instance used to send HTTP requests.
     */
    private final HttpRequest httpRequest;

    /**
     * Constructs a new SendVerificationEmail instance with the given HttpRequest instance.
     * @param httpRequest The HttpRequest instance to use for sending HTTP requests.
     */
    public SendVerificationEmail(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    /**
     * Sends a verification email to the given email address using a POST request.
     *
     * @param email The email address to send the verification email to.
     * @param cookie The cookie string to use in the request headers.
     * @return true if the email was sent successfully, false otherwise.
     * @throws IOException if an error occurs while sending the HTTP request.
     */
    public boolean send(String email, String cookie) throws IOException {
        RequestBody body = new FormBody.Builder()
                .add("email", email)
                .build();

        // Send a POST request to the JetBrains signup-request endpoint with the email and cookie in the request body and headers, respectively.
        Response jbResponse = httpRequest.postRequest("https://account.jetbrains.com/signup-request?_st=" + cookie, "_st-JBA=" + cookie, body);

        int responseStatus = jbResponse.code();

        // Return true if the response status code is 200, indicating a successful email send.
        return responseStatus == 200;
    }
}
