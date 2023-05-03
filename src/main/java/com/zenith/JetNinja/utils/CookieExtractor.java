package com.zenith.JetNinja.utils;

import org.springframework.stereotype.Component;


@Component
public class CookieExtractor {

    /**
     * Extracts the first cookie value from the response header of an HTTP response.
     *
     * @param response the HTTP response object to extract cookies from
     * @return the cookie value
     * @throws IllegalArgumentException if the response does not contain any cookies
     */

    public String getCookieValue(String cookie){

        String[] cookieParts = cookie.split("=");
        String cookieValue = cookieParts[1];
        String[] cookieValueParts = cookieValue.split(";");

        return cookieValueParts[0];
    }
}
