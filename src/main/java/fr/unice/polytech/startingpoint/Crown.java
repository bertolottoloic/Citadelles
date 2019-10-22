package fr.unice.polytech.startingpoint;

public class Crown {
    Player player;

    Player getCrownOwner(){
        return this.player;
    }

    void goesTo(Player p){
        this.player = p;
    }


}
