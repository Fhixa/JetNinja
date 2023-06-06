package com.zenith.JetNinja.utils;

import com.zenith.JetNinja.constants.Colors;

import java.util.function.Consumer;

public class Status {
    public static Consumer<String> warning = warningTxt -> System.out.println(Colors.TEXT_YELLOW + "\nWarning: " + Colors.TEXT_RESET + warningTxt + Colors.TEXT_YELLOW + "   ⚠" + Colors.TEXT_RESET);
    public static Consumer<String> error = errTxt -> System.out.println(Colors.TEXT_RED + "\nError: " + Colors.TEXT_RESET + errTxt + Colors.TEXT_RED + "   ✘" + Colors.TEXT_RESET);
    public static Runnable success = () -> System.out.println(Colors.TEXT_GREEN + "   success ✔" + Colors.TEXT_RESET);
    private Status() {
    }


}

