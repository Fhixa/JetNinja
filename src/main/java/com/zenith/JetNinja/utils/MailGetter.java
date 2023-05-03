package com.zenith.JetNinja.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zenith.JetNinja.model.GeneratedEmail;
import com.zenith.JetNinja.model.MailData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class MailGetter {
    private final HttpRequest httpRequest;
    public MailGetter(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    //Email generator
    public GeneratedEmail generateMail(String url)  {

        Map<String, String> response = null;
        try {
            response = httpRequest.getRequest(url, "");
        } catch (IOException e) {
            Status.error("Rate limited. Please use VPN or PROXY and try again.");
            System.exit(0);
        }
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.fromJson(response.get("body"), GeneratedEmail.class);

    }

    //Email grabber
    public MailData grabMail(String url) throws IOException {

        String response = "{\"email\":[]}";

        while (response.equals("{\"email\":[]}")) {
            response = httpRequest.getRequest(url, "").get("body");
        }

        //map data to json
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        //return mail data
        return gson.fromJson(response, MailData.class);

    }

}