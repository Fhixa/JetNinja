package com.zenith.JetNinja.utils.automation;

import com.zenith.JetNinja.utils.HttpRequest;
import com.zenith.JetNinja.utils.PreLoader;
import com.zenith.JetNinja.utils.Status;
import com.zenith.JetNinja.utils.TypeWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Component
public class AutoUpdate {

    private final HttpRequest httpRequest;
    private final TypeWriter typeWriter;
    private  final PreLoader preLoader;
    // Read the fileUrl from the config.properties file
    @Value("${application.latestJnUrl}")
    String latestJnUrl;
    @Value("${application.version}")
    double current_version;

    public AutoUpdate(HttpRequest httpRequest, TypeWriter typeWriter, PreLoader preLoader) {
        this.httpRequest = httpRequest;
        this.typeWriter = typeWriter;
        this.preLoader = preLoader;
    }
    public void  start() throws IOException, InterruptedException {
        typeWriter.type("Checking for update....", 50);
        preLoader.start();
        if (!isLatest()) {

            /*final Properties properties = new Properties();*/

            // Get the target folder (current working directory)
            /*String targetFolder = System.getProperty("user.dir");*/

            // Download the latest version JAR file
            String fileName = getFileNameFromUrl(latestJnUrl);
            String filePath = "./" + fileName;
            try {

                downloadFile(latestJnUrl, filePath);

            } catch (IOException e) {
                e.printStackTrace();
            }
            runJarFile(filePath);
        }
        preLoader.stop();
        Status.success();
    }
   private boolean isLatest() throws IOException {

        String response = httpRequest.getRequestVersion("https://raw.githubusercontent.com/ZenithSuite/JetNinja/master/src/main/resources/version.txt");

       String versionString = response.split("=")[1].trim();
       double version = Double.parseDouble(versionString);

       return current_version == version;

   }

    private String getFileNameFromUrl(String fileUrl) {
        String[] parts = fileUrl.split("/");
        return parts[parts.length - 1];
    }

    private void downloadFile(String fileUrl, String filePath) throws IOException {
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
