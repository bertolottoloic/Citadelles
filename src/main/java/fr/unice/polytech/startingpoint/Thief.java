package fr.unice.polytech.startingpoint;

public class Thief extends Role {

    Thief(){
        super(2);
    }

    void action(Role c){
        if(c.getPlayer()!=null){
            //player.giveMoneyTo(this);
        }
    }

    @Override
    void useSpecialPower() {
        action(this.player.getTargetToRob());

    }
}
