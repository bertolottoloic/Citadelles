package fr.unice.polytech.startingpoint.board;


import fr.unice.polytech.startingpoint.game.DealRoles;
import fr.unice.polytech.startingpoint.player.Player;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.Arrays;

public class Board{
    private Deck deck;
    private Bank bank;
    private ArrayList<Player> players;
    private DealRoles dealRoles;

    public Board(){
        this.deck=new Deck();
        this.bank = new Bank();
    }

    Bank getBank() {
        return bank;
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

    public boolean withdraw(int i,Player player) {
            return bank.withdraw(i, player);
    }
    public District draw() {
        return deck.withdraw();
    }

    public boolean deposit(int cost, Player player) {
        return bank.deposit(cost,player);
    }

    public ArrayList<District> exchangeMany(ArrayList<District> hand) {
        return deck.exchangeMany(hand);
    }

    public int numberOfCardsOfDeck() {
        return this.deck.numberOfCards();
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
        bank.setBourses(players);
    }
    public void setPlayers(Player ... players) {
        this.players=new ArrayList<>();
        this.players.addAll(Arrays.asList(players));
        bank.setBourses(this.players);
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

    //TODO deplace to Bot ??
    public Player getPlayerWithTheBiggestHand(){
        Player player = this.getPlayers().get(0);
        for(Player players : this.getPlayers()) {
            if(players.getHand().size()>player.getHand().size()){
                player = players;
            }
        }
        return player;
    }

    //TODO deplace and rewrite ??
    public Player getPlayerWithTheBiggestCity(){
        Player player = this.getPlayers().get(0);
        if(player.getCharacter().toString().equals("Bishop")) player = player.getNextPlayer();
        for(Player players : this.getPlayers()) {
            if(!(players.getCharacter().toString().equals("Bishop"))){
                if(players.getCity().getSizeOfCity()>player.getCity().getSizeOfCity()){
                    player = players;
                } else if(players.getCity().getSizeOfCity()==player.getCity().getSizeOfCity() && players.getCity().getTotalValue()>player.getCity().getTotalValue()){
                    player = players;
                }
            }
        }
        return player;
    }

	public Role getRole(String roleName) {
		return this.dealRoles.getRole(roleName);
	}

	public int getPlayerMoney(Player player) {
        return bank.getPlayerMoney(player);
	}

    public void distributeCoinsAtBeggining(){
        bank.distributeCoinsAtBeggining();
    }

}