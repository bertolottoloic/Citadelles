package fr.unice.polytech.startingpoint;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String... args) throws FileNotFoundException,IOException{
        Player p1= new Player(1);
        Player p2= new Player(2);
        Player p3= new Player(3);
        Player p4= new Player(4);

        Game game=new Game(p1,p2,p3,p4);

        //Une mini-mini partie avec l'assasin qui tue le voleur


    }


}
