package fr.unice.polytech.startingpoint;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Bishop extends Role{

    Bishop(){
        super(5);
    }

    void action(Role c){
    }

    @Override
    void useSpecialPower() {
        // TODO Auto-generated method stub

    }
    void districtBenefits(){
        Set<District> st = new HashSet<District>(player.getCity());
        int bonusGold = Collections.frequency(player.getCity(), "religion");
        if(bonusGold > 0){
            numberGold+=bonusGold;
        } else {
            numberGold = 0;
        }
    }

}
