package com.javazen.jetninja;

import com.javazen.jetninja.constants.Colors;
import com.javazen.jetninja.model.GeneratedEmail;
import com.javazen.jetninja.model.MailData;
import com.javazen.jetninja.utils.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;


@Component
public class GetAccount implements CommandLineRunner {
    private final CookieScraper cookieScraper;
    private final SendVerificationEmail sendVerificationEmail;
    private  final MailGetter emailGetter;
    private  final  GrabVerificationLink grabVerificationLink;
    private final TypeWriter typeWriter;

    public GetAccount(CookieScraper cookieScraper, SendVerificationEmail sendVerificationEmail, MailGetter emailGetter, GrabVerificationLink grabVerificationLink, TypeWriter typeWriter) {
        this.cookieScraper = cookieScraper;
        this.sendVerificationEmail = sendVerificationEmail;
        this.emailGetter = emailGetter;
        this.grabVerificationLink = grabVerificationLink;
        this.typeWriter = typeWriter;
    }

    @Override
    public void run(String... args) {

        //tool starts here..
        typeWriter.type(Colors.TEXT_PURPLE + "Developed by Zenith\n" + Colors.TEXT_RESET, 100);
        typeWriter.type("∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎\n", 100);


        try {
            //grab cookie
            typeWriter.type(Colors.TEXT_CYAN + "Checking requirements...." + Colors.TEXT_RESET, 50);
            String cookie = cookieScraper.getCookie("https://account.jetbrains.com/signup");

            //generate temp email
            GeneratedEmail generatedEmail = emailGetter.generateMail("https://api.tempmail.lol/generate");
            Status.success();
            /*String email = generatedEmail.getAddress();
            String token = generatedEmail.getToken();*/

            Scanner sc = new Scanner(System.in);
            System.out.print("Email And Token: ");
            String email = sc.next();
            String token = sc.next();

            //send verification link to email
            System.out.print("Preparing to bypass email verification....");
            boolean isSentVerificationEmail = sendVerificationEmail.send(email, cookie);
            if(isSentVerificationEmail){
                Status.success();
                System.out.print("Bypassing email verification....");
            }

            //get email message data
            String url = "https://api.tempmail.lol/auth/" + token;
            MailData mailData = emailGetter.grabMail(url);

            //grab link from email
            String verificationLink = grabVerificationLink.getLink(mailData);
            Status.success();
            System.out.println(verificationLink);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
