package com.zenith.JetNinja.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zenith.JetNinja.model.GeneratedEmail;
import com.zenith.JetNinja.model.MailData;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailGetter {
    private final PreLoader preLoader;
    private final HttpRequest httpRequest;
    public MailGetter(PreLoader preLoader, HttpRequest httpRequest) {
        this.preLoader = preLoader;
        this.httpRequest = httpRequest;
    }

    //Email generator
    public GeneratedEmail generateMail(String url)  {

        String responseBody = null;
        try {
            responseBody = httpRequest.getRequestWBody(url);
        } catch (IOException e) {
            Status.error("Rate limited. Please use VPN or PROXY and try again.");
            System.exit(0);
        }
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        return gson.fromJson(responseBody, GeneratedEmail.class);

    }

    //Email grabber
    public MailData grabMail(String url) throws IOException {

        String responseBody = "{\"email\":[]}";

        while (responseBody.equals("{\"email\":[]}")) {
            responseBody = httpRequest.getRequestWBody(url);
        }

        //map data to json
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        //return mail data
        return gson.fromJson(responseBody, MailData.class);

    }

}