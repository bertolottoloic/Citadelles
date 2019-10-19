package fr.unice.polytech.startingpoint;

public class Thief extends Role {

    Thief(){
        super(2);
        this.isStolen = false;
    }

    void action(Role c){
        if(!c.isMurdered() && !(c instanceof Murderer) && !(c instanceof Thief)){
            c.stolen();
        }
    }

    @Override
    void useSpecialPower() {
        action(this.player.getTargetToRob());

    }
}
