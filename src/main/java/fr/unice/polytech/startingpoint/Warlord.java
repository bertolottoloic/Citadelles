package fr.unice.polytech.startingpoint;

public class Warlord extends Role {

    Warlord(Player player){
        super(player,8);
    }

    void action(Player target,District toDestroy){
        target.deleteDistrictFromHand(toDestroy);
    }
}
