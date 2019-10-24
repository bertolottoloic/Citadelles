package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

class Board{
    private Deck deck;
    private Bank bank;
    private Crown crown;
    ArrayList<Player> players;
    Board(){
        this.crown=new Crown();
        this.deck=new Deck();
        this.bank = new Bank();

    }

    Bank getBank() {
        return bank;
    }
    Crown getCrown() {
        return crown;
    }
    Deck getDeck() {
        return deck;
    }



    public boolean canWithdraw(int i) {
        return true;
    }

    public ArrayList<District> withdrawMany(int i) {
        return deck.withdrawMany(i);
    }

    public int withdraw(int i) {
            if (bank.canWithdraw(i)){
                bank.withdraw(i);
                return i;
            }
            return 0;
    }

    public void deposit(int cost) {
        bank.deposit(cost);
    }

    public ArrayList<District> exchangeMany(Hand hand) {
        return deck.exchangeMany(hand);
    }

    public int numberOfCardsOfDeck() {
        return this.deck.numberOfCards();
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}