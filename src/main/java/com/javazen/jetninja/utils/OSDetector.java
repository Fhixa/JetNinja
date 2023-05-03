package com.javazen.jetninja.utils;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OSDetector {
    public void detect() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")){
            System.out.println("Windows");
        }
        else if (os.contains("osx")){
            System.out.println("MacOS");
        }
        else if (os.contains("nix") || os.contains("aix") || os.contains("nux")){

        }
    }
}
