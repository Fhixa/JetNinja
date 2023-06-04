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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.Properties;



@Component
public class GetAccount implements CommandLineRunner {
    private final SendVerificationEmail sendVerificationEmail;
    private  final MailGetter emailGetter;
    private  final  GrabVerificationLink grabVerificationLink;
    private final TypeWriter typeWriter;
    private  final PreLoader preLoader;
    private final HttpRequest httpRequest;
    private  final Register register;
    private final AutoUpdate autoUpdate;

    public GetAccount(SendVerificationEmail sendVerificationEmail, MailGetter emailGetter, GrabVerificationLink grabVerificationLink, TypeWriter typeWriter, PreLoader preLoader, HttpRequest httpRequest, Register register, Login login, AutoUpdate autoUpdate ) {
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

boolean isLatest;
        try {
            isLatest = autoUpdate.check_for_update();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (!isLatest) {
            System.out.println("Less download the latest version");
             final Properties properties = new Properties();



            // Read the fileUrl from the config.properties file
            try (InputStream inputStream = JetNinjaApplication.class.getClassLoader().getResourceAsStream("config.properties")) {
                properties.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            String latestJnUrl = properties.getProperty("latest_jn_url");

            // Get the target folder (current working directory)
            String targetFolder = System.getProperty("user.dir");

            // Download the latest version JAR file
            String fileName = getFileNameFromUrl("https://github.com/ZenithSuite/JetNinja/releases/download/JetNinjaV1/JetNinja.Beta.Release.-.Version.1.0.jar");
            String filePath = targetFolder + "\\" + fileName;
            try {

                downloadFile("https://github.com/ZenithSuite/JetNinja/releases/download/JetNinjaV1/JetNinja.Beta.Release.-.Version.1.0.jar", filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
            runJarFile(filePath);

        }else {

            //tool starts here..
            typeWriter.type(Colors.TEXT_CYAN + "Developed by Zenith\n" + Colors.TEXT_RESET, 100);
            typeWriter.type("∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎∎\n", 100);


            try {
                //grab cookie
                typeWriter.type("Checking requirements....", 50);
                preLoader.start();
                Map<String, String> response = httpRequest.getRequest("https://account.jetbrains.com/signup", "");
                String stCookie = response.get("stCookie");
                String jbCookie = response.get("jbCookie");

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
                boolean isSentVerificationEmail = sendVerificationEmail.send(email, stCookie);
                if (isSentVerificationEmail) {
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
                String badFormUrl = register.getFormUrl(verificationLink, jbCookie, stCookie);
                String formSubmitUrl = badFormUrl.replace("amp;", "");

                //submit form with user details
                List<String> loginDetails = register.submitForm(formSubmitUrl, jbCookie, stCookie);
                String username = loginDetails.get(0);
                String password = loginDetails.get(1);


                System.out.println("username: " + Colors.TEXT_GREEN + username + Colors.TEXT_RESET + "\npassword: " + Colors.TEXT_GREEN + password + Colors.TEXT_RESET);

            } catch (UnknownHostException unknownHostException) {
                Status.error("\nSomething went wrong. Please check your internet connection and try again");

            } catch (Exception e) {
                Status.error("\nSomething went wrong... Please try again!");
            }

        }

    }

    private static String getFileNameFromUrl(String fileUrl) {
        String[] parts = fileUrl.split("/");
        return parts[parts.length - 1];
    }

    private static void downloadFile(String fileUrl, String filePath) throws IOException {
        URL url = new URL(fileUrl);
        try (InputStream in = url.openStream()) {
            long copied = Files.copy(in, Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);
            if (copied != -1) {
                System.out.println("Successfully Downloaded");
            }else {
                System.out.println("Something went wrong");
            }
        }
    }

    private static void runJarFile(String jarFilePath) {
        try {
            Process process = Runtime.getRuntime().exec("java -jar " + jarFilePath);
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}