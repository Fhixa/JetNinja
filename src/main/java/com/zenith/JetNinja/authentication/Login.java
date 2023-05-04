package com.zenith.JetNinja.authentication;

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
public class Login {
    private final HttpRequest httpRequest;

    // Constructor to inject the HttpRequest dependency
    public Login(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    // Method to retrieve the login URL and challenge code
    public List<String> getLoginUrl(String OauthUrl, String jbCookie, String stCookie) throws IOException {

        // Send GET request to the OAuth URL with the necessary cookies
        Map<String, String> responseMap = httpRequest.getRequest(OauthUrl, "JSESSIONID-JBA=" + jbCookie + ";_st-JBA=" + stCookie);

        // Extract the HTML response body
        String bodyData = responseMap.get("body");

        // Define a regex pattern to match the login form action URL
        String regex = "<form method=\"post\" action=\"(.*?)\" class=\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bodyData);

        // Define a regex pattern to match the login challenge code
        String challengeCodeRegex = "login_challenge=(.*?)";
        Pattern chPattern = Pattern.compile(challengeCodeRegex);
        Matcher chMatcher = pattern.matcher(OauthUrl);

        // Return a list with the login form action URL and the challenge code (if found)
        return List.of("https://account.jetbrains.com" + ((matcher.find()) ? matcher.group(1) : ""), ((chMatcher.find()) ? chMatcher.group(1) : ""));
    }

    // Method to submit the login form
    public void submit(String url, String jbCookie, String stCookie, String challenge, String username, String password) throws IOException {
        // Build the request body with the challenge code, username, password, and an empty second factor
        RequestBody body = new FormBody.Builder()
                .add("challenge", challenge)
                .add("username", username)
                .add("password", password)
                .add("secondFactor", "")
                .build();

        // Send POST request to the login form action URL with the necessary cookies and request body
        Response response = httpRequest.postRequest(url, "JSESSIONID-JBA=" + jbCookie + ";_st-JBA=" + stCookie, body);

        // Output the response status code to the console
        System.out.println(response.code());
    }
}
