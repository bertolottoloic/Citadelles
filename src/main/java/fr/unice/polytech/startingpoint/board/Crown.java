package fr.unice.polytech.startingpoint.board;

import fr.unice.polytech.startingpoint.player.Player;

public class Crown {
    Player player;

    public Player getCrownOwner(){
        return this.player;
    }

    public void goesTo(Player p){
        this.player = p;
    }

}
