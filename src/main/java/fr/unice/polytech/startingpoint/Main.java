package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.game.Manager;
import fr.unice.polytech.startingpoint.player.*;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String... args) throws FileNotFoundException,IOException{
    	
        Player p1=new BotRnd(1);
        Player p2=new BotRnd(2);
        Player p3=new BotIA(3);
        Player p4=new BotIA(4);

        Manager manager=new Manager();
        manager.letsPlay(p1,p2,p3,p4);

    }


}
