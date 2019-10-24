package fr.unice.polytech.startingpoint;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    
    public static void main(String... args) throws FileNotFoundException,IOException{
    
        Player p1=new Player(1);
        Player p2=new Player(2);
        Player p3=new Player(3);
        Player p4=new Player(4);
        Player p5=new Player(5);
        Player p6=new Player(6);

        Manager.letsPlay(p1,p2,p3,p4,p5,p6);

    }


}
