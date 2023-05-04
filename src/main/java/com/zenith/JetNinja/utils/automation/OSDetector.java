package com.zenith.JetNinja.utils.automation;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.event.KeyEvent;


@Component
public class OSDetector {

    public enum OperatingSystem {
        WINDOWS,
        MAC,
        LINUX,
        UNKNOWN
    }

    public OperatingSystem detectOS() throws AWTException, InterruptedException {
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

}



