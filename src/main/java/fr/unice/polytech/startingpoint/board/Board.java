package fr.unice.polytech.startingpoint.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

import fr.unice.polytech.startingpoint.player.Player;

/**
 * @author Patrick Anagonou 
 * @author Heba Bouzidi
 * @author Loic Bertolotto
 * @author Clement Poueyto
 *
 * */

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

    public Player playerWithTheBiggestHand(Player except){
        return this.players.stream().filter(p->p!=except).max(
            (p1,p2)->Integer.compare(p1.sizeOfHand(),p1.sizeOfHand())
        ).get();
        
    }

    public Player playerWithTheBiggestCity(Player except){
        Comparator<Player> critere1=(p1,p2)->Integer.compare(p1.sizeOfCity(),p2.sizeOfCity());
        Comparator<Player> critere2=(p1,p2)->Integer.compare(p1.totalValueOfCity(),p2.totalValueOfCity());
        return this.players.stream().filter(p->p!=except).max(
            critere1.thenComparing(critere2)
        ).get();
    }
  
    public Player richestPlayer(Player except){
        return this.players.stream().filter(p->p!=except).max(
            (p1,p2)->Integer.compare(p1.getGold(),p1.getGold())
        ).get();
    }

    public Player randomPlayer(){
        var random=new Random();
        return players.get(random.nextInt(players.size()));
    }
    public Player randomPlayer(Player except){
        return players.stream().filter(p->p!=except).findAny().get();
    }

	public Player existsGraveyardPlayer() {
		for(Player p : players) {
			if(p.cityHasTheDistrict("Cimetiere")) {
				return p;
			}
		}
		return null;
	}

}