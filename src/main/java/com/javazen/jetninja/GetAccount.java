package com.javazen.jetninja;

import com.javazen.jetninja.utils.CookieScraper;
import com.javazen.jetninja.utils.HttpRequest;
import okhttp3.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GetAccount implements CommandLineRunner {
    private final CookieScraper cookieScraper;
    private final HttpRequest httpRequest;

    public GetAccount(CookieScraper cookieScraper, HttpRequest httpRequest) {
        this.cookieScraper = cookieScraper;
        this.httpRequest = httpRequest;
    }

    @Override
    public void run(String... args) {
        try {
            //get cookie
            String cookie = cookieScraper.getCookie("https://account.jetbrains.com/signup");

            //print cookie
            System.out.println(cookie);

            //send verification link to mail
            Response jbResponse = httpRequest.postRequest("https://account.jetbrains.com/signup-request?_st=" + cookie, "_st-JBA=" + cookie);

            //get response status code
            int responseStatus = jbResponse.code();

            //check response status code
            if (responseStatus != 200) {
                System.out.println("ERROR");
            }else{
                System.out.println("okkkk");

            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
