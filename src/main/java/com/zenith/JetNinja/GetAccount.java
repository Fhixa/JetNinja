package com.zenith.JetNinja;

import com.zenith.JetNinja.constants.Colors;
import com.zenith.JetNinja.model.MailData;
import com.zenith.JetNinja.utils.*;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Scanner;


@Component
public class GetAccount implements CommandLineRunner {
    private final SendVerificationEmail sendVerificationEmail;
    private  final MailGetter emailGetter;
    private  final  GrabVerificationLink grabVerificationLink;
    private final TypeWriter typeWriter;
    private  final PreLoader preLoader;
    private final HttpRequest httpRequest;
    private  final Register confirmEmaill;

    public GetAccount(SendVerificationEmail sendVerificationEmail, MailGetter emailGetter, GrabVerificationLink grabVerificationLink, TypeWriter typeWriter, PreLoader preLoader, HttpRequest httpRequest, Register confirmEmaill) {
        this.sendVerificationEmail = sendVerificationEmail;
        this.emailGetter = emailGetter;
        this.grabVerificationLink = grabVerificationLink;
        this.typeWriter = typeWriter;
        this.preLoader = preLoader;
        this.httpRequest = httpRequest;
        this.confirmEmaill = confirmEmaill;
    }

    @Override
    public void run(String... args) {

        //tool starts here..
        typeWriter.type(Colors.TEXT_CYAN + "Developed by Zenith\n" + Colors.TEXT_RESET, 100);
        typeWriter.type("∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎\n", 100);


        try {
            //grab cookie
            typeWriter.type("Checking requirements....", 50);
            /*preLoader.start();*/
            Map<String, String> response = httpRequest.getRequest("https://account.jetbrains.com/signup", "");
            String stCookie = response.get("stCookie");
            String jbCookie = response.get("jbCookie");

            //generate temp email
            /*GeneratedEmail generatedEmail = emailGetter.generateMail("https://api.tempmail.lol/generate");
            preLoader.stop();
            Status.success();
            String email = generatedEmail.address();
            String token = generatedEmail.token();*/

            Scanner sc = new Scanner(System.in);
            System.out.print("Email And Token: ");
            String email = sc.next();
            String token = sc.next();

            //send verification link to email
            preLoader.start();
            System.out.print("Preparing to bypass email verification....");
            boolean isSentVerificationEmail = sendVerificationEmail.send(email, stCookie);
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

            //get form submit url
            String badFormUrl = confirmEmaill.getFormUrl(verificationLink, jbCookie, stCookie);
            String formSubmitUrl = badFormUrl.replace("amp;", "");
            System.out.println(formSubmitUrl);

            //submit form with user details and login
            List<String> loginDetails = confirmEmaill.submitForm(formSubmitUrl, jbCookie, stCookie);
            if(loginDetails != null){
                String OauthUrl = sc.next();
                Map<String, String> responseMap = httpRequest.getRequest(OauthUrl, "JSESSIONID-JBA=" + jbCookie + ";_st-JBA=" + stCookie);

                System.out.println(responseMap);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
