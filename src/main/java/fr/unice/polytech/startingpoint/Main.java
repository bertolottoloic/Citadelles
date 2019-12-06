package fr.unice.polytech.startingpoint;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.unice.polytech.startingpoint.game.Manager;
import fr.unice.polytech.startingpoint.player.*;

public class Main {
    private static HashMap<Integer,Integer> stats=new HashMap<>();
    private static Logger resume;
    private static Logger statistic;
    private static int numberOfGames = 1000;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format",
                "%5$s %n");
        resume = Logger.getLogger("Running");
        statistic = Logger.getLogger("Statistic");
    }

    public static void main(String... args) throws IllegalArgumentException,FileNotFoundException,IOException {
        resume.setLevel(Level.OFF);
        statistic.setLevel(Level.INFO);
        Player p1 = null;
        Player p2 = null;
        Player p3 = null;
        Player p4 = null;
        int n=0;
        stats.put(1,0);
        stats.put(2,0);
        stats.put(3,0);
        stats.put(4,0);
        while (n < numberOfGames) {
            p1 = new BotRainbow(1);
            p2 = new BotRainbow(2);
            p3 = new BotSpender(3);
            p4 = new BotSpender(4);
            Manager manager = new Manager();
            manager.letsPlay(p1, p4,p2, p3);
            countWinner(manager.getWinner());
            n++;
        }
        statistic.log(Level.INFO,results(p1,p2,p3,p4));
    }

    private static void countWinner(ArrayList<Player> winner) {
        for(Player p :winner){
            stats.put(p.getId(),stats.get(p.getId())+1);
        }
    }

    private static String results(Player...players){
        String res = "Pourcentages de victoires des bots sur "+numberOfGames+" parties :\n";
        DecimalFormat df = new DecimalFormat("0.0");
        for(Integer e : stats.keySet()) {
            res += players[e-1].getClass().getSimpleName() + " = " + df.format(((((double)stats.get(e))/numberOfGames)*100)) + " %\n";
        }
        return res;
    }
}
