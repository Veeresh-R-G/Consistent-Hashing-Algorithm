package com.example.consistenthashingalgorithm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.NoSuchAlgorithmException;

@SpringBootApplication
public class ConsistentHashingAlgorithmApplication {

    public static void main(String[] args) throws NoSuchAlgorithmException, InterruptedException {
        ConsistentHashing ch = new ConsistentHashing(3);

        ch.addServer("Server-1");
        ch.addServer("Server-2");
        ch.addServer("Server-3");
        ch.addServer("Server-4");
        ch.addServer("Server-5");
        ch.addServer("Server-6");
        ch.addServer("Server-7");
        System.out.println("Servers Added on the ring :)");
        System.out.println("--------------------------------------------------------");
        Thread.sleep(2000);



        for (int keyVal = 0; keyVal < 10; keyVal++) {
            System.out.printf("Key%d: is present on server : %s \n" , keyVal, ch.getServer("Key" + keyVal));
        }

        System.out.println("Oops...Seems like Server - 6 failed ");
        ch.removeServer("Server-6");
        System.out.println("Shutting Server-6 down ....");
        Thread.sleep(2200);

        System.out.println("Re-routing the keys ...");
        Thread.sleep(1200);

        for (int keyVal = 0; keyVal < 10; keyVal++) {
            System.out.printf("Key%d: is present on server : %s \n" , keyVal, ch.getServer("Key" + keyVal));
        }

        System.out.println("Thank you :)");
    }
}
