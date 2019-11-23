package fr.unice.polytech.startingpoint.player;


import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.Random;

public class BotRnd extends Player{
    private Random random=new Random();

    public BotRnd(int id){
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
        targetToDestroyDistrict = pickRandomTargetPlayer();
        districtToDestroy = pickRandomDistrict();
        super.specialMove();
    }

    @Override
    protected void action() {
        int i =0;
            ArrayList<District> currHand=new ArrayList<District>();
            currHand.addAll(getHand().toList());
            int nb=getCharacter().getNumberDistrictBuildable();
            for (District d : currHand){
                if ((d.getCost() < getGold()) && i<nb) {
                    addToTheCity(d);
                    i++;
                }
            }
            if(checkFinishBuilding() || this.deck.numberOfCards()<=0){
                /*
                Notez que si il reste encore des cartes dans le deck et
                que le joueur a bien atteint  les 8 districts sans être le premier à
                l'avoir fait, ce bloc n'est pas executé
                Cela ne pose pas problème puisque le Manager n'est notifié qu'une
                seule fois du fait que le jeu doit prendre fin au lieu de plusieurs
                fois
                */
                support.firePropertyChange("gameOver",gameOver , true);
		        this.gameOver=true;//inutile en fait : c'est là pour le principe
            }
        
    }

    @Override
    public void discard(ArrayList<District> d){
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

    Role pickRandomTargetRole(){
        return  this.dealRoles.getRole(random.nextInt(8));
    }
    Player pickRandomTargetPlayer(){
        return getBoard().getPlayers().get(random.nextInt(4));
    }
    District pickRandomDistrict() {
        ArrayList<District> hand = new ArrayList<District>(getBoard().getPlayers().get(random.nextInt(4)).getCity().getListDistricts());
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