package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

public class BotIAHighCost extends Player {
    private Random random=new Random();

    public BotIAHighCost(int id){
        super(id);
    }

    @Override
    public void chooseRole() {
        if(!alreadyChosenRole){
            ArrayList<Role> leftRoles=board.getDealRoles().getLeftRoles();
            String maxColor=bestColorDistrict();
            Role choosenRole=bestRoleToChoose(leftRoles,maxColor);
            board.getDealRoles().getLeftRoles().remove(choosenRole);
            setCharacter(choosenRole);
            alreadyChosenRole=true;
            //appelle le prochain player
            if(nextPlayer!=null){
                nextPlayer.chooseRole();
            }

        }
    }

    Role bestRoleToChoose(ArrayList<Role> roles, String color){
        int position;
        switch (color){
            case "religion": position=5;
            break;
            case "militaire": position =8;
            break;
            case "noble": position =4;
            break;
            default : position =6;
        }
        for(Role role : roles){
            if(role.getPosition()==position){
                return role;
            }
        }
        return roles.get(0);

    }

    String bestColorDistrict(){
        HashMap<String,Integer> countColors=new HashMap<String, Integer>();
        countColors.put("religion",0);
        countColors.put("commerce",0);
        countColors.put("militaire",0);
        countColors.put("noble",0);
        countColors.put("merveille",0);
        for (District d : getHand()){
             int count = countColors.containsKey(d.getColor()) ? countColors.get(d.getColor()) : 0;
            countColors.put(d.getColor(), count + 1);
        }
        for (String color :countColors.keySet()){
            if(countColors.get(color)==Collections.max(countColors.values())){
                return color;
            }
        }
        return "commerce";
    }

    @Override
    public void specialMove() {
        targetToKill=pickTargetRole();
        targetToRob=pickTargetRole();
        targetToExchangeHandWith=pickTargetPlayer();
        targetToDestroyDistrict = pickTargetPlayer();
        districtToDestroy = pickRandomDistrict();
        super.specialMove();
    }

    @Override
    protected void action() {
        int i =0;
        ArrayList<District> currHand=new ArrayList<District>(getHand());
        int nb=getCharacter().getNumberDistrictBuildable();
        if(!getHand().isEmpty()) {
            District toBuild = whatToBuild();
            if (toBuild != null) {
                addToTheCity(toBuild);
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

    District highCostDistrict(ArrayList<District> hand,int golds) {
        District highCost=hand.get(0);
        for(District d : hand){
            if(d.getCost() > highCost.getCost()&&(d.getCost()<=golds)) {
                highCost=d;
            }
        }
        return highCost;
    }

    District whatToBuild(){
        District district = highCostDistrict(getHand(),getGold());
        if(district.getCost()<=getGold()){
            return district;
        }
        else{return null;}
    }

    @Override
    public void discard(ArrayList<District> d){
        District discard;
        if(d.size()>=2){
            if(d.get(0).getCost()>getGold()){discard=d.get(0);}
            else if(d.get(1).getCost()>getGold()){discard=d.get(1);}
            else {
                if(d.get(0).getCost()>d.get(1).getCost()){discard=d.get(1);}
                else{discard=d.get(0);}
            }
            d.remove(discard);
            getBoard().getDeck().putbackOne(discard);
        }
        //TODO : Cartes "Merveille" Manufacture, Observatoire, Bibliothèque
    }

    @Override
    public boolean coinsOrDistrict() {
        if(getHand().size()<2){
            return false;}
        else
            { return true;}
    }

    @Override
    protected boolean isBuildingFirst() {
        if(getCharacter() instanceof Wizard){ //pioche 3 cartes avant de jouer
            return true;}
        else if(getCharacter() instanceof Warlord){ //si la main du magicien est mauvaise active son pouvoir, sinon il construit avant
            int countBadCards=nbBadCards(getHand(),getGold());
            if(countBadCards>getHand().size()/2){return false;} // si plus de la moitié des cartes sont "mauvaises" active son pouvoir
            else{return true;}
        }
        else
        {return false;}
    }

    int nbBadCards(ArrayList<District>hand , int golds){
        int count=0;
        for(District d : hand){
            if(d.getCost()+3<golds||d.getCost()>golds){
                count++;
            }
        }
        return count;
    }

    Role pickTargetRole(){
        Role character = this.getCharacter();
        ArrayList<Role> roles = this.getBoard().getRoles();
        Role target;
        switch(character.getPosition()){
            case 1:
                target = roles.get(1);
                break;
            case 2:
                target = roles.get(5);
                break;
            default :
                target = null;
                break;
        }
        return target;
    }

    Player pickTargetPlayer(){
        Role character = this.getCharacter();
        Player target;
        switch(character.getPosition()){
            case 3:
                target = this.getBoard().getPlayerWithTheBiggestHand();
                break;
            case 8:
                target = this.getBoard().getPlayerWithTheBiggestCity();
                break;
            default :
                target = null;
                break;
        }
        return target;
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