package fr.unice.polytech.startingpoint.role;

import java.util.logging.Level;

public class Murderer extends Role {

    public Murderer(){
        super(1);
    }

    private void action(Role c){
        c.murdered();
        logger.log(Level.INFO,toString()+" ( joueur numero "+ player.getId()+" )"+"tue le "+c.toString());
    }

    void stolen(){
        return;
    }

    void murdered(){
        return;
    }

    

    @Override
    public void useSpecialPower() {
        this.action(this.player.getTargetToKill());

    }

    

}
