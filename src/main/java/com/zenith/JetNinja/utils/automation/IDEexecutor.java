package com.zenith.JetNinja.utils.automation;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.KeyEvent;


@Component
public class IDEexecutor {


    private final OSDetector osDetector;

    public IDEexecutor(OSDetector osDetector) {
        this.osDetector = osDetector;
    }


    public void detect() throws AWTException, InterruptedException {

        switch (osDetector.detectOS()) {
            case WINDOWS:
                automateExec("Telegram", KeyEvent.VK_WINDOWS, KeyEvent.VK_ENTER);
                break;
            case MAC:
                automateExec("Telegram", KeyEvent.VK_META, KeyEvent.VK_SPACE);
                break;
            case LINUX:
                automateExec("Telegram", KeyEvent.VK_CONTROL, KeyEvent.VK_SPACE);
                break;
            default:
                System.out.println("Unsupported operating system");
                return;
        }

    }
    private static void automateExec(String searchQuery, int OS_SEARCH_BAR, int OS_ENTER) throws InterruptedException, AWTException {
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
}
