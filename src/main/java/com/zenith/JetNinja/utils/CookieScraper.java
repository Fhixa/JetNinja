package com.zenith.JetNinja.utils;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


/**
 * This class provides a utility for extracting cookies from HTTP responses using OkHttp client library.
 */
@Component
public class CookieScraper {

    /**
     * Makes an HTTP request to the given URL and extracts the cookie value from the response.
     *
     * @param url the URL to make the request to
     * @return the cookie value
     * @throws IOException if the request fails
     */

    public List<String> getCookies(Response response) {
        List<String> grabbedCookies = response.headers("Set-Cookie");
        if (grabbedCookies.isEmpty()) {
            return null;
        }
        return grabbedCookies;
    }

}
