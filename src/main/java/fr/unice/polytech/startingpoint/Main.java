package fr.unice.polytech.startingpoint;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static String hello() {
        return "Hello World!";
    }

    public static void main(String... args) throws FileNotFoundException,IOException{
        System.out.println(System.getProperties().get("user.dir"));

        Deck d=new Deck();
        System.out.println(d.getList());
        
    }


}
