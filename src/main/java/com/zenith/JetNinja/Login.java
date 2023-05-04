package com.zenith.JetNinja;

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

    public Login(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public List<String> getLoginUrl(String OauthUrl, String jbCookie, String stCookie) throws IOException {

        Map<String, String> responseMap = httpRequest.getRequest(OauthUrl, "JSESSIONID-JBA=" + jbCookie + ";_st-JBA=" + stCookie);

        String bodyData = responseMap.get("body");
        String regex = "<form method=\"post\" action=\"(.*?)\" class=\"";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(bodyData);

        String challengeCodeRegex = "login_challenge=(\\w+)";
        Pattern chPattern = Pattern.compile(challengeCodeRegex);
        Matcher chMatcher = chPattern.matcher(OauthUrl);

        String urlPart = matcher.find() ? matcher.group(1) : "";
        String challengeId = chMatcher.find() ? chMatcher.group(1) : "";

        return List.of("https://account.jetbrains.com" + urlPart, challengeId);

    }
    public void submit(String url, String jbCookie, String stCookie, String challenge, String username, String password) throws IOException {
        RequestBody body = new FormBody.Builder()
                .add("challenge", challenge)
                .add("username", username)
                .add("password", password)
                .add("secondFactor", "")
                .build();

        Response response = httpRequest.postRequest(url, "JSESSIONID-JBA=" + jbCookie + ";_st-JBA=" + stCookie, body);
        System.out.println(response.code());
    }
}
