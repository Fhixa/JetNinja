package com.javazen.jetninja.utils;

import org.springframework.stereotype.Component;

@Component
public class TypeWriter {
    public void type(String text, int delay){
        int i;
        for(i = 0; i < text.length(); i++){
            System.out.print(text.charAt(i));
            try{
                Thread.sleep(delay);
            }catch(InterruptedException ex){
                Thread.currentThread().interrupt();
            }
        }
    }
}
