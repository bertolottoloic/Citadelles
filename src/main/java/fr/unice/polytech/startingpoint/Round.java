package fr.unice.polytech.startingpoint;


import java.util.ArrayList;

import static fr.unice.polytech.startingpoint.Game.gameOver;

public class Round {
    private ArrayList<Player> players;
    private static int numberRound=0;
    private ArrayList<Role> visibleRoles;

    public Round(ArrayList<Player> p, Deck deck){
        this.players=p;
        numberRound++;
        dealRoles(players);
        for(Player player : players){
            System.out.println(player.getCharacter());
        }
        playTurns(players);
        gameOver=false;

    }

    int getNumberRound(){
        return numberRound;
    }

    ArrayList<Role> getVisibleRoles(){
        return this.visibleRoles;
    }

    ArrayList<Player> getPlayers(){ return this.players;}



    void dealRoles(ArrayList<Player> players){
        dealRole dr=new dealRole(players);
    }

    void playTurns(ArrayList<Player> players){
        for(Player p : players){
            Turn turn= new Turn(p);
        }
        gameOver = true;
    }



}