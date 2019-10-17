package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Merchant extends Role {

    Merchant(){
        super(6);
        this.numberGold++;
    }

    void action(Role c){
    }

    @Override
    void useSpecialPower() {
        // TODO Auto-generated method stub
    }

    void districtBenefits(){
        Set<District> st = new HashSet<District>(player.getCity());
        int bonusGold = Collections.frequency(player.getCity(), "commerce");
        if(bonusGold > 0){
            numberGold+=bonusGold;
        } else {
            numberGold = 0;
        }
    }
}
