package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

class Board{
    Deck deck;
    Bank bank;
    ArrayList<Player> players;
    Crown crown;
    Board(ArrayList<Player> p){
        this.crown=new Crown();
        this.deck=new Deck();
        this.bank = new Bank();
        this.players=p;
        crown.goesTo(selectRandomPlayer());

    }

    Bank getBank() {
        return bank;
    }
    ArrayList<Player> getPlayers() {
        return players;
    }
    Crown getCrown() {
        return crown;
    }
    Deck getDeck() {
        return deck;
    }

    Player selectRandomPlayer(){
        Random rand = new Random();
        int random = rand.nextInt(players.size());
        return players.get(random);
    }


}