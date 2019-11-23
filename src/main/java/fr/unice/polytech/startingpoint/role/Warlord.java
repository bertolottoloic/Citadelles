package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.player.Player;

public class Warlord extends Role {

    public Warlord(){
        super(8);
        this.setColor(DistrictColor.Warlord);
    }

    void action(Player target, District toDestroy){
        target.deleteDistrictFromCity(toDestroy);
        //le joueur paie le prix du district -1
		this.player.getBank().deposit(toDestroy.getCost()-1,this.player);
		
        System.out.println(this+" (joueur numéro "+this.player.getId()+") détruit le district \n"+this.player.getDistrictToDestroy()+" du joueur numéro "+this.player.getTargetToDestroyDistrict().getId());
    }

    @Override
    public void useSpecialPower() {
        //TODO pourquoi cette vérif ??
        if(this.player.getDistrictToDestroy()!= null && this.player.getDistrictToDestroy().getCost()<this.player.getGold()) {
            action(this.player.getTargetToDestroyDistrict(), this.player.getDistrictToDestroy());
        }
    }

    
}
