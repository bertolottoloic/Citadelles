package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.HashMap;

class Game {
    private ArrayList<Player> players=new ArrayList<Player>();
    static Boolean gameOver=true;
    private HashMap<Player,Integer> points;
    Board board;

     Game(Player ... players){
        
        for (Player p : players){
            this.players.add(p);
        }

        while(gameOver){
            new DealRoles(this.players);
            playAllTurns();
        }
        this.points=new HashMap<Player, Integer>();
        for(Player p : players){
            countPoints(p);
        }

    }

    void startGame(){
        dealCards(4);
        dealGolds(2);
        Board board=new Board(this.players);
    }


    void playAllTurns(){
         int i=0;
         int index = players.indexOf(board.getCrown().getCrownOwner());
         while(i<players.size()){
             if(index==players.size()){index=0;}
             //playTurn(players.get(index));
             index++;
             i++;
         }
    }

    /*void playTurn(Player p){
         Role role = p.getCharacter();
        if(!role.isMurdered()) {
            if(role.isStolen()){

            }
            p.start();
            if(p.actionorBuild()==1){
                p.action();
                p.build();
            }
            else{
                p.build();
                p.action();
            }
        }
    }*/


    Boolean getGameOver(){ return this.gameOver;}
    HashMap<Player,Integer> getPoints(){return this.points;}
    ArrayList<Player> getPlayers(){ return this.players;}

    void dealCards(int n){
        for(Player p : players){
            for(int i=0;i<n;i++){
                p.pickNewDistrict(Assets.TheDeck.withdraw());
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