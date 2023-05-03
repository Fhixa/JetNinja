package com.zenith.JetNinja.utils;

import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * This class provides a utility for extracting cookies from HTTP responses using OkHttp client library.
 */
@Component
public class CookieScraper {
    private final HttpRequest httpRequest;
    private final CookieExtractor cookieExtractor;

    public CookieScraper(HttpRequest httpRequest, CookieExtractor cookieExtractor) {
        this.httpRequest = httpRequest;
        this.cookieExtractor = cookieExtractor;
    }



    /**
     * Makes an HTTP request to the given URL and extracts the cookie value from the response.
     *
     * @param url the URL to make the request to
     * @return the cookie value
     * @throws IOException if the request fails
     */
    public String getCookie(String url) throws IOException {
        String jbCookie;
        Response response = httpRequest.getRequest(url);
        jbCookie = cookieExtractor.getCookie(response);

        return jbCookie;
    }

}
