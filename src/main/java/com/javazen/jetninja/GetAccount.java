package com.javazen.jetninja;

import com.javazen.jetninja.model.Email;
import com.javazen.jetninja.model.GeneratedEmail;
import com.javazen.jetninja.model.MailData;
import com.javazen.jetninja.utils.CookieScraper;
import com.javazen.jetninja.utils.MailGetter;
import com.javazen.jetninja.utils.HttpRequest;
import okhttp3.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class GetAccount implements CommandLineRunner {
    private final CookieScraper cookieScraper;
    private final HttpRequest httpRequest;
    private  final MailGetter emailGetter;

    public GetAccount(CookieScraper cookieScraper, HttpRequest httpRequest, MailGetter emailGetter) {
        this.cookieScraper = cookieScraper;
        this.httpRequest = httpRequest;
        this.emailGetter = emailGetter;
    }

    @Override
    public void run(String... args) {
        try {
            //get cookie
            String cookie = cookieScraper.getCookie("https://account.jetbrains.com/signup");


            //generate temp mail
            GeneratedEmail generatedEmail = emailGetter.generateMail("https://api.tempmail.lol/generate");

            //send verification link to mail
            Response jbResponse = httpRequest.postRequest("https://account.jetbrains.com/signup-request?_st=" + cookie, "_st-JBA=" + cookie, generatedEmail.getAddress());


            //get response status code
            int responseStatus = jbResponse.code();

            //check response status code
            if (responseStatus != 200) {
                System.out.println("ERROR");
            }else{
                System.out.println(generatedEmail.getToken());
                String token = generatedEmail.getToken();
                String url = "https://api.tempmail.lol/auth/" + token;

                MailData mailData = emailGetter.grabMail(url);
                String htmlConfirmAcc = mailData.getEmail().get(0).getHtml();
                String regex = "<a href=\"(.*?)\">Confirm your account</a>";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(htmlConfirmAcc);

                String text = "";

                if (matcher.find()) {
                    text = matcher.group(1);
                }

                System.out.println(text);
            }

        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
