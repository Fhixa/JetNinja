package com.zenith.JetNinja.utils;

import org.springframework.stereotype.Component;

import static java.lang.Thread.sleep;

@Component
public class PreLoader {
    private Thread loaderThread;

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

   public void start(){
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

   public void stop() throws InterruptedException {
        loaderThread.interrupt();
       System.out.print("\b");
   }

}
