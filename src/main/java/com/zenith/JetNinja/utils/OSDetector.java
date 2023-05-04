package com.zenith.JetNinja.utils;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.List;
import java.util.stream.*;

import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class OSDetector {
    public static void detect() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            System.out.println("Windows");
        } else if (os.contains("osx")) {
            System.out.println("MacOS");
        } else if (os.contains("nix") || os.contains("aix") || os.contains("nux")) {

        }
    }

    public static void main(String[] args) throws IOException, AWTException, InterruptedException {
        String searchQuery = "IntelliJ IDEA 2023.1";
        Robot robot = new Robot();


        robot.keyPress(KeyEvent.VK_WINDOWS);
        robot.keyRelease(KeyEvent.VK_WINDOWS);
        Thread.sleep(1000);


        for (char c : searchQuery.toCharArray()) {
            robot.keyPress(Character.toUpperCase(c));
            robot.keyRelease(Character.toUpperCase(c));
            Thread.sleep(50);
        }

        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
    }
}


