package com.zenith.JetNinja.utils;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.KeyEvent;


@Component
public class OSDetector {

    private enum OperatingSystem {
        WINDOWS,
        MAC,
        LINUX,
        UNKNOWN
    }

    private static OperatingSystem getOperatingSystem() throws AWTException, InterruptedException {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("win")) {
            return OperatingSystem.WINDOWS;
        } else if (osName.contains("mac")) {
            return OperatingSystem.MAC;
        } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            return OperatingSystem.LINUX;
        } else {
            return OperatingSystem.UNKNOWN;
        }

    }

        public static void detect() throws AWTException, InterruptedException {
            switch (getOperatingSystem()) {
                case WINDOWS:
                    automateOPEN("Telegram", KeyEvent.VK_WINDOWS, KeyEvent.VK_ENTER);
                    break;
                case MAC:
                    automateOPEN("Notepad", KeyEvent.VK_META, KeyEvent.VK_SPACE);
                    break;
                case LINUX:
                    automateOPEN("TeamViewer", KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE);

                    break;
                default:
                    System.out.println("Unsupported operating system");
                    return;
            }

        }

    private static void automateOPEN( String searchQuery,int OS_SEARCH_BAR, int OS_ENTER) throws InterruptedException, AWTException {
        Robot robot = new Robot();

        robot.keyPress(OS_SEARCH_BAR);
        robot.keyRelease(OS_SEARCH_BAR);
        Thread.sleep(1000);

        for (char c : searchQuery.toCharArray()) {
            robot.keyPress(Character.toUpperCase(c));
            robot.keyRelease(Character.toUpperCase(c));
            Thread.sleep(50);
        }

        robot.keyPress(OS_ENTER);
        robot.keyRelease(OS_ENTER);
    }

    public static void main(String[] args) throws AWTException, InterruptedException {
        detect();
    }
    }



