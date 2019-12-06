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
    private static HashMap<String,Integer> stats=new HashMap<>();
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
        int n=0;
        stats.put("BotRnd",0);
        stats.put("BotBuildFast",0);
        stats.put("BotSpender",0);
        stats.put("BotRainbow",0);
        while (n < numberOfGames) {
            Player p1 = new BotRnd(1);
            Player p2 = new BotBuildFast(2);
            Player p3 = new BotSpender(3);
            Player p4 = new BotRainbow(4);
            Manager manager = new Manager();
            manager.letsPlay(p1, p4,p2, p3);
            countWinner(manager.getWinner());
            n++;
        }
        statistic.log(Level.INFO,results());
    }

    private static void countWinner(ArrayList<Player> winner) {
        for(Player p :winner){
            stats.put(p.getClass().getSimpleName(),stats.get(p.getClass().getSimpleName())+1);
        }
    }

    private static String results(){
        String res = "";
        DecimalFormat df = new DecimalFormat("0.0");
        for(String e : stats.keySet()) {
            res += e + " : " + df.format(((((double)stats.get(e))/numberOfGames)*100)) + "\n";
        }
        return res;
    }
}
