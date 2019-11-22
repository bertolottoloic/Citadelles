package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.DistrictColor;

public class Bishop extends Role{

    public Bishop(){
        super(5);
        this.setColor(DistrictColor.Religion);
    }

    void action(Role c){
    }

    @Override
    public void useSpecialPower() {

    }
    

}
