package fr.unice.polytech.startingpoint;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static String hello() {
        return "Hello World!";
    }

    public static void main(String... args) throws FileNotFoundException,IOException{
        Player p1= new Player(1);
        Game game=new Game(p1);
        
    }


}
