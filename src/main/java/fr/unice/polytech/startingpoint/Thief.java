package fr.unice.polytech.startingpoint;

public class Thief extends Role {

    Thief(){
        super(2);
        this.isStolen = false;
    }

    void action(Role c){
        if(!c.hasBeenMurdered() && !(c.equals(Assets.TheMurderer)) && !(c.equals(Assets.TheThief))){
            c.isStolen();
        }
    }

    @Override
    void useSpecialPower() {
        action(this.player.getTargetToRob());

    }
}
