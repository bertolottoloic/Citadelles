package fr.unice.polytech.startingpoint.player;


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
            int nb=getCharacter().getNumberDistrictBuildable();
            for (District d : currHand){
                if ((d.getCost() < getGold()) && i<nb) {
                    addToTheCity(d);
                    i++;
                }
            }
            if(checkFinishBuilding() || getBoard().numberOfCardsOfDeck()<=0){
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
    public void Discard(ArrayList<District> d){
        if(!d.isEmpty()){
            getBoard().getDeck().putbackOne(d.remove(0));
        }
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
        return getBoard().getRole(random.nextInt(8));
    }
    Player pickRandomTargetPlayer(){
        return getBoard().getPlayers().get(random.nextInt(4));
    }
}