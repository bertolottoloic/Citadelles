package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

class Board{
    private Deck deck;
    private Bank bank;
    private ArrayList<Player> players;
    private Crown crown;
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
}