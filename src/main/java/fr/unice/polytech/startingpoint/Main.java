package fr.unice.polytech.startingpoint;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    public static void main(String... args) throws FileNotFoundException,IOException{
        //Player p1= new Player(1);
        //Game game=new Game(p1);

        Player p1=new Player(1);
        Player p2=new Player(2);
        p1.setCharacter(Assets.TheMurderer);
        p2.setCharacter(Assets.TheThief);
        p1.setTargetToKill(Assets.TheThief);
        p1.getCharacter().useSpecialPower();
        
        
    }


}
