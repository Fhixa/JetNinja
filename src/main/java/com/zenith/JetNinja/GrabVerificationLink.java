package com.zenith.JetNinja;

import com.zenith.JetNinja.model.MailData;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class extracts the verification link from the email body.
 */
@Component
public class GrabVerificationLink {

    private String text = null;

    /**
     * Returns the verification link found in the email body.
     *
     * @param mailData The email data containing the body.
     * @return The verification link found in the email body.
     */
    public String getLink(MailData mailData){

        String htmlConfirmAcc = mailData.email().get(0).html();
        String regex = "<a href=\"(.*?)\">Confirm your account</a>";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(htmlConfirmAcc);

        if (matcher.find()) {
            text = matcher.group(1);
        }

        return text;
    }
}
