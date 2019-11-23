package fr.unice.polytech.startingpoint.board;


import java.util.ArrayList;
import java.util.Arrays;

import fr.unice.polytech.startingpoint.player.Player;

public class Board{
    private ArrayList<Player> players;

    public Board(){
        
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

}