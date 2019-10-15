package fr.unice.polytech.startingpoint;

public class Merchant extends Role {

    Merchant(Player player){
        super(player,6);
        this.numberGold++;
    }

    void action(Role c){
    }
}
