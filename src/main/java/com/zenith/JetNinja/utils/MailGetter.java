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

    // Constructor injection
    public MailGetter(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    // Generates a new email with the given URL and returns the result as a GeneratedEmail object
    public GeneratedEmail generateMail(String url) {
        Map<String, String> response = null;
        try {
            // Sends a GET request to the given URL with an empty request body
            response = httpRequest.getRequest(url, "");
        } catch (IOException e) {
            // If an IOException occurs, log an error message and exit the application
            Status.error.accept("Rate limited. Please use VPN or PROXY and try again.");
            System.exit(0);
        }
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        Gson gson = builder.create();

        // Converts the JSON response to a GeneratedEmail object
        return gson.fromJson(response.get("body"), GeneratedEmail.class);
    }

    // Grabs the latest mail data from the given URL and returns it as a MailData object
    public MailData grabMail(String url) throws IOException {
        String response = "{\"email\":[]}";

        // Continues to send GET requests to the given URL until new email data is available
        while (response.equals("{\"email\":[]}")) {
            response = httpRequest.getRequest(url, "").get("body");
        }

        // Converts the JSON response to a MailData object
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();

        // Returns the MailData object
        return gson.fromJson(response, MailData.class);
    }
}
