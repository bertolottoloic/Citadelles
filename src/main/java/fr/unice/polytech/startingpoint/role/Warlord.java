package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.player.Player;

public class Warlord extends Role {

    public Warlord(){
        super(8);
        this.setColor("soldatesque");
    }

    void action(Player target, District toDestroy){
        target.deleteDistrictFromCity(toDestroy);
    }

    @Override
    public void useSpecialPower() {
        //action(this.player.getTargetToDestroyDistrict(), this.player.getDistrictToDestroy());

    }

    
}
