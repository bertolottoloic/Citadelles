package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.*;

public class King extends Role{

    King(){
        super(4);
    }

    void action(Crown c){
        c.goesTo(this.player);
    }

    void districtBenefits(){
        Set<District> st = new HashSet<District>(player.getCity());
        int bonusGold = Collections.frequency(player.getCity(), "noblesse");
        if(bonusGold > 0){
            numberGold+=bonusGold;
        } else {
            numberGold = 0;
        }
    }

    @Override
    void useSpecialPower() {
        // TODO Auto-generated method stub

    }
}
