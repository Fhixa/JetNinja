package com.zenith.JetNinja;

import com.zenith.JetNinja.constants.Colors;
import com.zenith.JetNinja.model.GeneratedEmail;
import com.zenith.JetNinja.model.MailData;
import com.zenith.JetNinja.utils.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class GetAccount implements CommandLineRunner {
    private final CookieScraper cookieScraper;
    private final SendVerificationEmail sendVerificationEmail;
    private  final MailGetter emailGetter;
    private  final  GrabVerificationLink grabVerificationLink;
    private final TypeWriter typeWriter;
    private  final PreLoader preLoader;

    public GetAccount(CookieScraper cookieScraper, SendVerificationEmail sendVerificationEmail, MailGetter emailGetter, GrabVerificationLink grabVerificationLink, TypeWriter typeWriter, PreLoader preLoader) {
        this.cookieScraper = cookieScraper;
        this.sendVerificationEmail = sendVerificationEmail;
        this.emailGetter = emailGetter;
        this.grabVerificationLink = grabVerificationLink;
        this.typeWriter = typeWriter;
        this.preLoader = preLoader;
    }

    @Override
    public void run(String... args) {

        //test

        //tool starts here..
        typeWriter.type(Colors.TEXT_CYAN+ "Developed by Zenith\n" + Colors.TEXT_RESET, 100);
        typeWriter.type("∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎\n", 100);


        try {
            //grab cookie
            typeWriter.type("Checking requirements....", 50);
            preLoader.start();
            String cookie = cookieScraper.getCookie("https://account.jetbrains.com/signup");

            //generate temp email
            GeneratedEmail generatedEmail = emailGetter.generateMail("https://api.tempmail.lol/generate");
            preLoader.stop();
            Status.success();
            String email = generatedEmail.address();
            String token = generatedEmail.token();

            /*Scanner sc = new Scanner(System.in);
            System.out.print("Email And Token: ");
            String email = sc.next();
            String token = sc.next();*/

            //send verification link to email
            preLoader.start();
            System.out.print("Preparing to bypass email verification....");
            boolean isSentVerificationEmail = sendVerificationEmail.send(email, cookie);
            if(isSentVerificationEmail){
                preLoader.stop();
                Status.success();
                System.out.print("Bypassing email verification....");
                preLoader.start();

            }

            //get email message data
            String url = "https://api.tempmail.lol/auth/" + token;
            MailData mailData = emailGetter.grabMail(url);

            //grab link from email
            String verificationLink = grabVerificationLink.getLink(mailData);
            preLoader.stop();
            Status.success();
            System.out.println(verificationLink);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
