package fr.unice.polytech.startingpoint.game;

import fr.unice.polytech.startingpoint.board.*;
import fr.unice.polytech.startingpoint.player.*;

import java.util.ArrayList;
import java.util.HashMap;

class Game {
    private ArrayList<Player> players=new ArrayList<>();
    private HashMap<Player,Integer> points;
    private Board board;

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
    
    /**
     * Setter of board needed solely for the JUnit tests
     * @param board
     */
	void setBoard(Board board) {
		this.board = board;
	}

	void startGame(){
        dealCards(4);
        dealGolds(2);
    }


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