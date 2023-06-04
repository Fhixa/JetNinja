package com.zenith.JetNinja.authentication;

import com.zenith.JetNinja.constants.UserDetails;
import com.zenith.JetNinja.utils.HttpRequest;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class represents the Register component responsible for creating a new JetBrains account by submitting a registration form.
 * It uses the OkHttp library for creating HTTP requests.
 *
 * The class is marked as a Spring Component so that it can be easily injected into other Spring-managed beans.
 */
@Component
public class Register {

    /**
     * The HttpRequest instance used to send HTTP requests.
     */
    private final HttpRequest httpRequest;

    /**
     * Constructs a new Register instance with the given HttpRequest instance.
     * @param httpRequest The HttpRequest instance to use for sending HTTP requests.
     */
    public Register(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    /**
     * Extracts the registration form URL from the given verification link.
     *
     * @param verificationLink The verification link received in the verification email.
     * @param jbCookie The JetBrains cookie string to use in the request headers.
     * @param stCookie The st cookie string to use in the request headers.
     * @return The URL of the registration form.
     * @throws IOException if an error occurs while sending the HTTP request.
     */
    public String getFormUrl(String verificationLink, String jbCookie, String stCookie) throws IOException {
        Map<String, String> register = httpRequest.getRequest(verificationLink, "JSESSIONID-JBA=" + jbCookie + ";_st-JBA=" + stCookie);
        String bodyData = register.get("body");
        String regex = "<form method=\"post\" action=\"(.*?)\" js-password-strength-url=";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bodyData);

        return "https://account.jetbrains.com" + ((matcher.find()) ? matcher.group(1) : "");
    }

    /**
     * Submits the registration form to create a new JetBrains account.
     *
     * @param formSubmitUrl The URL of the registration form.
     * @param jbCookie The JetBrains cookie string to use in the request headers.
     * @param stCookie The st cookie string to use in the request headers.
     * @return A List of Strings containing the username and password of the newly created account if the registration is successful, or null otherwise.
     * @throws IOException if an error occurs while sending the HTTP request.
     */
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

        if(response.code() == 200) {
            return List.of(username, password);
        } else {
            return Collections.emptyList();
        }
    }
}
