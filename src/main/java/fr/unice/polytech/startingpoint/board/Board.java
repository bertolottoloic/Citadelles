package fr.unice.polytech.startingpoint.board;


import fr.unice.polytech.startingpoint.game.DealRoles;
import fr.unice.polytech.startingpoint.player.Player;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.Arrays;

public class Board{
    private Deck deck;
    private Bank bank;
    private Crown crown;
    private ArrayList<Player> players;
    private DealRoles dealRoles;

    public Board(){
        this.deck=new Deck();
        this.bank = new Bank();
    }

    Bank getBank() {
        return bank;
    }
    public Crown getCrown() {
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
        District district=deck.withdraw();
        return district;
    }

    public void deposit(int cost) {
        bank.deposit(cost);
    }

    public ArrayList<District> exchangeMany(ArrayList<District> hand) {
        return deck.exchangeMany(hand);
    }

    public int numberOfCardsOfDeck() {
        return this.deck.numberOfCards();
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
    public void setPlayers(Player ... players) {
        this.players=new ArrayList<>();
        this.players.addAll(Arrays.asList(players));

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

    public DealRoles getDealRoles() {
        return dealRoles;
    }

    public Player getPlayerWithTheBiggestHand(){
        Player player = this.getPlayers().get(0);
        for(Player players : this.getPlayers()) {
            if(players.getHand().size()>player.getHand().size()){
                player = players;
            }
        }
        return player;
    }

    public Player getPlayerWithTheBiggestCity(){
        Player player = this.getPlayers().get(0);
        for(Player players : this.getPlayers()) {
            if(players.getCity().getSizeOfCity()>player.getCity().getSizeOfCity()){
                player = players;
            }
        }
        return player;
    }

	public Role getRole(String roleName) {
		return this.dealRoles.getRole(roleName);
	}

	public void setCrown(Crown c){
        this.crown = c;
    }

}