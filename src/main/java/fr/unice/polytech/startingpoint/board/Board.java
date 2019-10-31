package fr.unice.polytech.startingpoint.board;


import fr.unice.polytech.startingpoint.player.Hand;
import fr.unice.polytech.startingpoint.player.Player;
import fr.unice.polytech.startingpoint.role.*;

import java.util.ArrayList;

public class Board{
    private Deck deck;
    private Bank bank;
    private Crown crown;
    private ArrayList<Player> players;
    private ArrayList<Role> roles = new ArrayList<>();

    public Board(){
        this.crown=new Crown();
        this.deck=new Deck();
        this.bank = new Bank();
        this.roles.add(new Murderer());
        this.roles.add(new Thief());
        this.roles.add(new Wizard());
        this.roles.add(new King());
        this.roles.add(new Bishop());
        this.roles.add(new Merchant());
        this.roles.add(new Architect());
        this.roles.add(new Warlord());

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

    public ArrayList<Role> getRoles(){
        return new ArrayList<Role>(this.roles);
    }

    public Role getRole(int index){
        return this.roles.get(index);
    }

    public void reInitializeRoles(){
        for(Role r:roles){
            r.reInitialize();
        }

    }
}