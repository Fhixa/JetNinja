package com.javazen.jetninja.utils;

import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CookieExtractor {

    /**
     * Extracts the first cookie value from the response header of an HTTP response.
     *
     * @param response the HTTP response object to extract cookies from
     * @return the cookie value
     * @throws IllegalArgumentException if the response does not contain any cookies
     */
    public String getCookie(Response response) {
        List<String> grabbedCookies = response.headers("Set-Cookie");
        if (grabbedCookies.isEmpty()) {
            throw new IllegalArgumentException("Response does not contain any cookies.");
        }
        String mainCookie = grabbedCookies.get(0);
        String[] cookieParts = mainCookie.split("=");
        String cookieValue = cookieParts[1];
        String[] cookieValueParts = cookieValue.split(";");

        return cookieValueParts[0];
    }
}
