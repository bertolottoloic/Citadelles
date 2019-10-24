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
        Iterator<Role> it =Assets.leftRoles.iterator();

        while(it.hasNext()){
            Role r=it.next();

            if(r.toString().equals("Murderer")){
                setCharacter(r);
                it.remove();
                nextPlayer.chooseRole();
                return;
            }
        }

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

            for (District d : getHand()) {
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