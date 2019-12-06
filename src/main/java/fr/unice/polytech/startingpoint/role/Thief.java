package fr.unice.polytech.startingpoint.role;

import java.util.logging.Level;

public class Thief extends Role {

    public Thief(){
        super(2);
        this.isStolen = false;
    }

    void stolen(){
        return;
    }

    private void action(Role c){
        if (!c.isMurdered()) {
            c.stolen();
            logger.log(Level.INFO,toString() + " ( joueur numero " + player.getId() + " )" + "vole le " + c.toString());
        }
    }

    @Override
    public void useSpecialPower() {
        action(this.player.getTargetToRob());

    }
}
