package com.javazen.jetninja.utils;

import com.javazen.jetninja.constants.Colors;

public class Status {
    public static void error(String text){
        System.out.println(Colors.TEXT_RED + "\nError: "  + Colors.TEXT_RESET + text + Colors.TEXT_RED + "   ✘" + Colors.TEXT_RESET );
    }
    public static void warning(String text){
        System.out.println(Colors.TEXT_YELLOW + "\nWarning: " + Colors.TEXT_RESET + text + Colors.TEXT_YELLOW + "   ⚠" + Colors.TEXT_RESET);
    }
    public static void success(){
        System.out.println(Colors.TEXT_GREEN + "   success ✔" + Colors.TEXT_RESET);
    }
}

