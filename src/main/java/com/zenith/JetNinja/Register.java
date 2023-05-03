package com.zenith.JetNinja;

import com.zenith.JetNinja.constants.UserDetails;
import com.zenith.JetNinja.utils.HttpRequest;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Register {
    private final HttpRequest httpRequest;

    public Register(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getFormUrl(String verificationLink, String jbCookie, String stCookie) throws IOException {
        Map<String, String> register = httpRequest.getRequest(verificationLink, "JSESSIONID-JBA=" + jbCookie + ";_st-JBA=" + stCookie);
        String bodyData = register.get("body");
        String regex = "<form method=\"post\" action=\"(.*?)\" js-password-strength-url=";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bodyData);

        return "https://account.jetbrains.com" + ((matcher.find()) ? matcher.group(1) : "");
    }
    public List<String> submitForm(String formSubmitUrl, String jbCookie, String stCookie) throws IOException {
        String firstName = UserDetails.firstName;
        String lastName = UserDetails.lastName;
        String username = UserDetails.generateUsername();
        String password = UserDetails.password;

        RequestBody body = new FormBody.Builder()
                .add("firstName", firstName)
                .add("lastName", lastName)
                .add("userName", username)
                .add("password", password)
                .add("pass2", password)
                .add("privacy", "true")
                .add("mkt.newsletter.customer", "false")
                .build();

        Response response = httpRequest.postRequest(formSubmitUrl, "JSESSIONID-JBA=" + jbCookie + ";_st-JBA=" + stCookie, body);

        if(response.code() == 200) return List.of(username, password);
        return null;
    }

}
