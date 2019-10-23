package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Random;

class Bot{
    Random random=new Random();

    Bot(){
    }

    int whatToPick(){
        return random.nextInt(2);
    }

    int whatToDoFirst(){
        return random.nextInt(2);
    }

    District whatToBuild(ArrayList<District> hand, int gold){
        for(District d : hand){
            if(d.getCost()<=gold){return d;}
        }
        return null;
    }

    int wantToActivate(){
        return random.nextInt(2);
    }

    Role whoToKill(){
        return Assets.getRoles().get(random.nextInt(8));
    }


    District whatToDestroy(Player p){
        if(p.getCity().size()>=1){
            return p.getCity().get(0);}
        return null;
    }
    Player whoseCityToDestroy(ArrayList<Player> players){
        return players.get(random.nextInt(2));
    }

    Role whoToSteal(){
        return Assets.getRoles().get(random.nextInt(8));
    }




}