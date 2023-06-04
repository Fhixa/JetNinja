package com.zenith.JetNinja.utils.automation;

import com.zenith.JetNinja.utils.HttpRequest;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class AutoUpdate {

    private final HttpRequest httpRequest;

    double current_version = 1.0;

    public AutoUpdate(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

   public boolean check_for_update() throws IOException {

        String response = httpRequest.getRequestVersion("https://raw.githubusercontent.com/ZenithSuite/JetNinja/master/src/main/resources/version.txt");

       String versionString = response.split("=")[1].trim();
       double version = Double.parseDouble(versionString);

       return current_version == version;

   }

}
