package fr.unice.polytech.startingpoint.board;


import fr.unice.polytech.startingpoint.game.DealRoles;
import fr.unice.polytech.startingpoint.player.Hand;
import fr.unice.polytech.startingpoint.player.Player;
import fr.unice.polytech.startingpoint.role.*;

import java.util.ArrayList;

public class Board{
    private Deck deck;
    private Bank bank;
    private Crown crown;
    private ArrayList<Player> players;
    private DealRoles dealRoles;

    public Board(){
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
    public Deck getDeck() {
        return deck;
    }



    public boolean canWithdraw(int i) {
        return bank.canWithdraw(i);
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
    public District draw() {
        if (deck.withdraw()!=null){
            return deck.withdraw();

        }
        return null;
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

    public void setDealRoles(DealRoles r){
        this.dealRoles = r;
    }

    public Role getRole(int index){
        return this.dealRoles.getRole(index);
    }

    public ArrayList<Role> getRoles(){
        return this.dealRoles.getRoles();
    }

}