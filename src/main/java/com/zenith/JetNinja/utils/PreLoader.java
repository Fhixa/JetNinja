package com.zenith.JetNinja.utils;

import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

@Component
public class PreLoader {
    private Thread loaderThread;

    // A private method that prints a loader animation to the console
    private static void loader() throws InterruptedException{
        System.out.print("-");
        sleep(300);
        System.out.print("\b");

        System.out.print("\\");
        sleep(300);
        System.out.print("\b");

        System.out.print("/");
        sleep(300);
        System.out.print("\b");
    }

    // A public method that starts the loader animation
    public void start(){
        // Create a new thread that runs the loader animation in a loop
        loaderThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    loader();
                }catch (InterruptedException e){
                    Thread.currentThread().interrupt();
                }
            }
        });
        loaderThread.start();
    }

    // A public method that stops the loader animation
    public void stop() throws InterruptedException {
        loaderThread.interrupt();
        System.out.print("\b");
    }
}
