package fr.unice.polytech.startingpoint.game;

import java.util.ArrayList;
import java.util.HashMap;

import fr.unice.polytech.startingpoint.player.Player;

class Game {
    private ArrayList<Player> players=new ArrayList<>();
    private HashMap<Player,Integer> points;

    Game(Player ... players){
        
        for (Player p : players){
            this.players.add(p);
        }

       /* while(true){
            new DealRoles(this.players);
        }
        
        for(Player p : players){
            countPoints(p);
        }*/

        points = new HashMap<Player, Integer>();
    }
    
    

	

    HashMap<Player,Integer> getPoints(){return this.points;}
    ArrayList<Player> getPlayers(){ return this.players;}


    

    
    public String toString(){
         String scores="";
         for(Player p : players){
             scores+="******************\n"+
                     "Player: "+p.getId()+"\n"+
                     "Points: "+points.get(p)+"\n"+
                     "******************\n";
         }
         return scores;
    }

}