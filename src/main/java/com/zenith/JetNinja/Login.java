package com.zenith.JetNinja;

import com.zenith.JetNinja.utils.HttpRequest;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class Login {
    private final HttpRequest httpRequest;

    public Login(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public String getChallengeId(String OauthUrl) {

        String challengeCodeRegex = "login_challenge=(\\w+)";
        Pattern chPattern = Pattern.compile(challengeCodeRegex);
        Matcher chMatcher = chPattern.matcher(OauthUrl);

        return chMatcher.find() ? chMatcher.group(1) : "";

    }
    public void submit(String jbCookie, String stCookie, String challenge, String username, String password) throws IOException {
        String url = "https://account.jetbrains.com/oauth2/signin?_st=" + stCookie;
        RequestBody body = new FormBody.Builder()
                .add("challenge", challenge)
                .add("username", username)
                .add("password", password)
                .add("secondFactor", "")
                .build();

        Response response = httpRequest.postRequest(url, "JSESSIONID-JBA=" + jbCookie + ";_st-JBA=" + stCookie, body);

    }
}
