package fr.unice.polytech.startingpoint;


import java.util.ArrayList;

import static fr.unice.polytech.startingpoint.Game.gameOver;

public class Round {
    private ArrayList<Player> players;
    private Deck deck;
    static int numberRound=0;

    public Round(ArrayList<Player> p, Deck deck){
        this.players=p;
        this.deck=deck;
        numberRound++;
        dealRoles(players);
        playTurns(players);

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