package com.zenith.JetNinja;

import com.zenith.JetNinja.authentication.Login;
import com.zenith.JetNinja.authentication.Register;
import com.zenith.JetNinja.constants.Colors;
import com.zenith.JetNinja.model.GeneratedEmail;
import com.zenith.JetNinja.model.MailData;
import com.zenith.JetNinja.utils.*;
import com.zenith.JetNinja.utils.automation.AutoUpdate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;


@Component
public class GetAccount implements CommandLineRunner {
    private final SendVerificationEmail sendVerificationEmail;
    private final MailGetter emailGetter;
    private final GrabVerificationLink grabVerificationLink;
    private final TypeWriter typeWriter;
    private final PreLoader preLoader;
    private final HttpRequest httpRequest;
    private final Register register;
    private final AutoUpdate autoUpdate;

    public GetAccount(SendVerificationEmail sendVerificationEmail, MailGetter emailGetter, GrabVerificationLink grabVerificationLink, TypeWriter typeWriter, PreLoader preLoader, HttpRequest httpRequest, Register register, Login login, AutoUpdate autoUpdate) {
        this.sendVerificationEmail = sendVerificationEmail;
        this.emailGetter = emailGetter;
        this.grabVerificationLink = grabVerificationLink;
        this.typeWriter = typeWriter;
        this.preLoader = preLoader;
        this.httpRequest = httpRequest;
        this.register = register;
        this.autoUpdate = autoUpdate;
    }

    @Override
    public void run(String... args) {

        //tool starts here..
        typeWriter.type(Colors.TEXT_CYAN + "Developed by Zenith.dev\n" + Colors.TEXT_RESET, 100);
        typeWriter.type("∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎\n", 100);


        try {

            autoUpdate.start();
            //grab cookie
            typeWriter.type("Checking requirements....", 50);
            preLoader.start();
            Map<String, String> response = httpRequest.getRequest("https://account.jetbrains.com/signup", "");
            String stCookie = response.get("stCookie");
            String jbCookie = response.get("jbCookie");

            //generate temp email
            GeneratedEmail generatedEmail = emailGetter.generateMail("https://api.tempmail.lol/generate");
            preLoader.stop();

            Status.success.get();
            String email = generatedEmail.address();
            String token = generatedEmail.token();

            /*Scanner sc = new Scanner(System.in);
            System.out.print("Email And Token: ");
            String email = sc.next();
            String token = sc.next();*/

            //send verification link to email
            preLoader.start();
            System.out.print("Preparing to bypass email verification....");
            boolean isSentVerificationEmail = sendVerificationEmail.send(email, stCookie);
            if (isSentVerificationEmail) {
                preLoader.stop();
                Status.success.get();
                System.out.print("Bypassing email verification....");
                preLoader.start();

            }

            //get email message data
            String url = "https://api.tempmail.lol/auth/" + token;
            MailData mailData = emailGetter.grabMail(url);

            //grab link from email
            String verificationLink = grabVerificationLink.getLink(mailData);
            preLoader.stop();
            Status.success.get();

            //get form submit url
            String badFormUrl = register.getFormUrl(verificationLink, jbCookie, stCookie);
            String formSubmitUrl = badFormUrl.replace("amp;", "");

            //submit form with user details
            List<String> loginDetails = register.submitForm(formSubmitUrl, jbCookie, stCookie);
            String username = loginDetails.get(0);
            String password = loginDetails.get(1);


            System.out.println("username: " + Colors.TEXT_GREEN + username + Colors.TEXT_RESET + "\npassword: " + Colors.TEXT_GREEN + password + Colors.TEXT_RESET);

        } catch (UnknownHostException unknownHostException) {
            Status.error.accept("\nSomething went wrong. Please check your internet connection and try again");

        } catch (Exception e) {
            Status.error.accept("\nSomething went wrong... Please try again!");
        }


    }


}