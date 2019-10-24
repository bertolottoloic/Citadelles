package fr.unice.polytech.startingpoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

class Bot extends Player{
    Random random=new Random();

    Bot(int id){
        super(id);
    }

    @Override
    public void chooseRole() {





                super.chooseRole();


    }

    @Override
    public void specialMove() {
        targetToKill=pickRandomTargetRole();
        targetToRob=pickRandomTargetRole();
        targetToExchangeHandWith=pickRandomTargetPlayer();
        super.specialMove();
    }

    @Override
    protected void action() {
        int i =0;
            ArrayList<District> currHand=new ArrayList<District>();
            currHand.addAll(getHand());
            for (District d : currHand){
                if ((d.getCost() < getGold())&& i<getCharacter().getNumberDistrictBuildable()) {
                    addToTheCity(d);
                    i++;
                }
            }
        super.action();
    }

    @Override
    boolean coinsOrDistrict() {
        if(random.nextInt(2)==1){
        return true;}
        else
            {return false;}
    }

    @Override
    protected boolean isBuildingFirst() {
        if(random.nextInt(2)==1){
            return true;}
        else
        {return false;}
    }

    Role pickRandomTargetRole(){
        return Assets.getRoles().get(random.nextInt(9));
    }
    Player pickRandomTargetPlayer(){
        return getBoard().getPlayers().get(random.nextInt(4));
    }
}