package com.javazen.jetninja.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.javazen.jetninja.model.GeneratedEmail;
import com.javazen.jetninja.model.Email;
import com.javazen.jetninja.model.MailData;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class MailGetter {
    private final HttpRequest httpRequest;

    public GeneratedEmail generateMail(String url) {
        try {
            String responseBody = httpRequest.getRequestWBody(url);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.create();
            GeneratedEmail emailGenerate = gson.fromJson(responseBody, GeneratedEmail.class);

            return emailGenerate;

        } catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }

    public synchronized  MailData grabMail(String url){
        try {
            System.out.println(url);
            Thread.sleep(2000);
            String responseBody = httpRequest.getRequestWBody(url);
            Thread.sleep(2000);
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();

            Gson gson = builder.create();
            MailData mailData = gson.fromJson(responseBody, MailData.class);


            return  mailData;

        } catch (IOException e) {

            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public MailGetter(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

}