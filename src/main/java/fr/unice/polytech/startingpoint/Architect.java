package fr.unice.polytech.startingpoint;

public class Architect extends Role {

    Architect(Player player){
        super(player,7);
        this.numberDistrict+=2;
    }

    void action(Role c){
    }
}
