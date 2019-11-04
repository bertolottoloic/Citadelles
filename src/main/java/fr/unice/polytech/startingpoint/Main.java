package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.game.Manager;
import fr.unice.polytech.startingpoint.player.Bot;
import fr.unice.polytech.startingpoint.player.Player;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {

    
    public static void main(String... args) throws FileNotFoundException,IOException{
    
        Player p1=new Bot(1);
        Player p2=new Bot(2);
        Player p3=new Bot(3);
        Player p4=new Bot(4);
        Player p5=new Bot(5);
        Player p6=new Bot(6);

        Manager manager=new Manager();
        manager.letsPlay(p1,p2,p3,p4);

    }


}
