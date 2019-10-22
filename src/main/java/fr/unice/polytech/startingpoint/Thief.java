package fr.unice.polytech.startingpoint;

public class Thief extends Role {

    Thief(){
        super(2);
        this.isStolen = false;
    }

    void stolen(){
        return;
    }

    void action(Role c){
        if (!c.isMurdered()) {
            c.stolen();
            System.out.println(toString() + " ( joueur numero " + player.getId() + " )" + "vole le " + c.toString());
        }
    }

    @Override
    void useSpecialPower() {
        action(this.player.getTargetToRob());

    }
}
