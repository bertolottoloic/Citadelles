package fr.unice.polytech.startingpoint;

public class Thief extends Role {

    Thief(Player player){
        super(player,2);
    }

    void action(Role c){
        if(c.getPlayer()!=null){
            //player.giveMoneyTo(this);
        }
    }
}
