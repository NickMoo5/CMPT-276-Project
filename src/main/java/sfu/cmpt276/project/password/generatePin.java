package sfu.cmpt276.project.password;

import java.util.Random;


public class generatePin {
    public static String genPin() {
        Random random = new Random();
        int pinInt = random.nextInt(900000) + 100000;
        String pin = String.valueOf(pinInt);
        
        return pin;
    }
}
