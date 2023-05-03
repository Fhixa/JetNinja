package com.zenith.JetNinja.constants;

import java.util.UUID;

public class UserDetails {
    public static final String firstName = "Zenith";
    public static final  String lastName = "Coders";
    public static final String password = "zEniTh1@9@#&08Fsdfasdrfa";

    public static String generateUsername(){
        return UUID.randomUUID().toString();
    }

}
