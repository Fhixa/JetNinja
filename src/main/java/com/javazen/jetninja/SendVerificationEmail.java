package com.javazen.jetninja;

import com.javazen.jetninja.utils.HttpRequest;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SendVerificationEmail {
    private final HttpRequest httpRequest;

    public SendVerificationEmail(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }
    //send verification link to mail
    public boolean send(String email, String cookie) throws IOException {
            RequestBody body = new FormBody.Builder()
                    .add("email", email)
                    .build();
            Response jbResponse = httpRequest.postRequest("https://account.jetbrains.com/signup-request?_st=" + cookie, "_st-JBA=" + cookie, body);


            //get response status code
            int responseStatus = jbResponse.code();

            //check response status code
        return responseStatus == 200;
    }
}
