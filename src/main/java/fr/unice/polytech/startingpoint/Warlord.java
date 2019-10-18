package fr.unice.polytech.startingpoint;

public class Warlord extends Role {

    Warlord(){
        super(8);
        this.setColor("soldatesque");
    }

    void action(Player target,District toDestroy){
        target.deleteDistrictFromHand(toDestroy);
    }

    @Override
    void useSpecialPower() {
        action(this.player.getTargetToDestroyDistrict(), this.player.getDistrictToDestroy());

    }

    
}
