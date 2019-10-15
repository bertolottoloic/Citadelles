package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.HashMap;

 class Game {
    private ArrayList<Player> players;
    static Boolean game=false;
    private Deck deck;
    private HashMap<Player,Integer> points;

     Game(Player ... players){
        this.players=new ArrayList<Player>();
        for (Player p : players){
            this.players.add(p);
        }

        this.deck=new Deck();

        dealCards(4);
        dealGolds(2);

        while(game){
            Round round=new Round(this.players);
        }
        this.points=new HashMap<Player, Integer>();
        for(Player p : players){
            countPoints(p);
        }

    }

    Boolean getGame(){ return this.game;}
    ArrayList<Player> getPlayers(){ return this.players;}

    void dealCards(int n){
        for(Player p : players){
            for(int i=0;i<n;i++){
                p.pickNewDistrict(deck.withdraw());
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