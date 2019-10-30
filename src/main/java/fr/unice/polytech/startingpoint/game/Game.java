package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.board.*;
import fr.unice.polytech.startingpoint.player.*;

import java.util.ArrayList;
import java.util.HashMap;

class Game {
    private ArrayList<Player> players=new ArrayList<>();
    static Boolean gameOver=true;
    private HashMap<Player,Integer> points;
    private Board board;

    Game(Player ... players){
        
        for (Player p : players){
            this.players.add(p);
        }

        while(gameOver){
            new DealRoles(this.players);
        }
        this.points=new HashMap<Player, Integer>();
        for(Player p : players){
            countPoints(p);
        }

    }

    void startGame(){
        dealCards(4);
        dealGolds(2);
    }


    Boolean getGameOver(){ return this.gameOver;}
    HashMap<Player,Integer> getPoints(){return this.points;}
    ArrayList<Player> getPlayers(){ return this.players;}

    void dealCards(int n){
        for(Player p : players){
            for(int i=0;i<n;i++){
                p.pickNewDistrict(board.draw());
            }
        }
    }

    void dealGolds(int n){
        for(Player p : players){
            p.addMoney(n);
        }
    }

    void countPoints(Player p){
        int point=0;
        for(District district : p.getCity()){
            point+=district.getValue();
        }
        points.put(p,point);
    }

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