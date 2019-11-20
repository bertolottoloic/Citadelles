package fr.unice.polytech.startingpoint;

import fr.unice.polytech.startingpoint.game.Manager;
import fr.unice.polytech.startingpoint.player.BotIA;
import fr.unice.polytech.startingpoint.player.BotIAHighCost;
import fr.unice.polytech.startingpoint.player.BotRnd;
import fr.unice.polytech.startingpoint.player.Player;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static HashMap<Integer,Integer> stats=new HashMap<>();

    public static void main(String... args) throws FileNotFoundException,IOException {
        int n=0;
        stats.put(1,0);
        stats.put(2,0);
        stats.put(3,0);
        stats.put(4,0);
        while (n < 1000) {
            Player p1 = new BotRnd(1);
            Player p2 = new BotRnd(2);
            Player p3 = new BotIA(3);
            Player p4 = new BotIAHighCost(4);

            Manager manager = new Manager();
            manager.letsPlay(p1, p2, p3, p4);
            countWinner(manager.getWinner());
            n++;

        }
        System.out.println(stats.toString());
    }

    private static void countWinner(ArrayList<Player> winner) {
        for(Player p :winner){
            stats.put(p.getId(),stats.get(p.getId())+1);
        }
    }

    /*public static void main(String... args) throws FileNotFoundException,IOException {
            Player p1 = new BotRnd(1);
            Player p2 = new BotRnd(2);
            Player p3 = new BotIA(3);
            Player p4 = new BotIAHighCost(4);

            Manager manager = new Manager();
            manager.letsPlay(p1, p2, p3, p4);
    }*/


}
