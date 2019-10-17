package fr.unice.polytech.startingpoint;

public class Thief extends Role {

    Thief(){
        super(2);
        this.isStolen = false;
    }

    void action(Role c){
        if(!c.hasBeenMurdered() && !(c instanceof Murderer) && !(c instanceof Thief)){
            c.isStolen();
        }
    }
}
