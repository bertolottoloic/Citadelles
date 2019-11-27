package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

public class BotRnd extends Player{
    private Random random=new Random();

    public BotRnd(int id){
        super(id);
    }

    
    @Override
    public Role processChooseRole() {
        // TODO Auto-generated method stub
        return super.processChooseRole();
    }

    @Override
    public District processDistrictToDestroy(Player target) {
        if(target!=null){
            return target.city.randomDistrict();
        }
        return null;
    }
    @Override
    public Player processWhoseDistrictToDestroy() {
        return board.randomPlayer(this);
    }

    @Override
    public Player processWhoToExchangeHandWith() {
        return board.randomPlayer(this);
    }
    

    @Override
    public List<District> processWhatToBuild() {
        int i =0;
        var toBuild=new ArrayList<District>();
        var currHand=new ArrayList<District>();
        currHand.addAll(getHand().toList());
        int nb=getCharacter().getNumberDistrictBuildable();
        for (District d : currHand){
            if ((d.getCost() < getGold()) && i<nb) {
                toBuild.add(d);
                i++;
            }
        }
        return toBuild;
    }
    @Override
    public void discard(List<District> d){
        if(!d.isEmpty()){
            this.deck.putbackOne(d.remove(0));
        }
    }

    @Override
    public boolean coinsOrDistrict() {
        if(random.nextInt(2)==1){
            return true;
        }
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

    @Override
    public boolean wantToUseFabric() {
        // TODO Auto-generated method stub
        return super.wantToUseFabric();
    }

    @Override
    public Role processWhoToKill() {
       return  this.dealRoles.getRole(random.nextInt(8)+1);
    }

    @Override
    public Role processWhoToRob() {
        return  this.dealRoles.getRole(random.nextInt(8)+1);
    }
    
    Player pickRandomTargetPlayer(){
        return board.randomPlayer();
    }
    District pickRandomDistrict() {
        ArrayList<District> hand = new ArrayList<District>(getBoard().randomPlayer().getCity().getListDistricts());
        if(!hand.isEmpty()) {
            District d = hand.get(0);
            for (District d1 : hand) {
                if (d1.getCost() < d.getCost()) d = d1;
            }
            return d;
        }
        return null;
    }
}