package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.game.Assets;
import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.Random;

public class Bot extends Player{
    private Random random=new Random();

    public Bot(int id){
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
    public boolean coinsOrDistrict() {
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
        return Assets.getRoles().get(random.nextInt(8));
    }
    Player pickRandomTargetPlayer(){
        return getBoard().getPlayers().get(random.nextInt(4));
    }
}