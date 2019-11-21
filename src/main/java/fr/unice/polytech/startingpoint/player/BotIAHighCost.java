package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;
import fr.unice.polytech.startingpoint.role.Warlord;
import fr.unice.polytech.startingpoint.role.Wizard;

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
        countColors.put("noblesse",0);
        countColors.put("merveille",0);
        for (District d : getHand().toList()){
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


    District whatToBuild(){
        District district = getHand().highCostDistrict(getGold());
        if(district.getCost()<=getGold()){
            return district;
        }
        else{
            return null;
        }
    }

    public void discard(ArrayList<District> d,int golds){
        if(d.size()>=2){
            d.sort((a,b)->
                    Integer.compare(a.getCost(),b.getCost())
            );
            while(d.size()>1){//On ne garde qu'une carte
                if(d.get(0).getCost()>golds){
                    getBoard().getDeck().putbackOne(d.remove(0));
                }
                else{
                    getBoard().getDeck().putbackOne(d.remove(d.size()-1));
                }
            }
        }
        //TODO : Cartes "Merveille" Manufacture, Observatoire, Bibliothèque
    }

    @Override
    public boolean coinsOrDistrict() {
        return getGold() < 2
                || hand.nbBadCards(getGold())<=hand.size()
                || city.getSizeOfCity() >= 6
                || board.getDeck().numberOfCards() < 4
                || hand.size()>2;
    }

    @Override
    protected boolean isBuildingFirst() {
        if(getCharacter().toString().equals("Wizard")){ //pioche 3 cartes avant de jouer
            return true;}
        else if(getCharacter().toString().equals("Warlord")){ //si la main du magicien est mauvaise active son pouvoir, sinon il construit avant
            int countBadCards=getHand().nbBadCards(getGold());
            if(countBadCards>getHand().size()/2){return false;} // si plus de la moitié des cartes sont "mauvaises" active son pouvoir
            else{return true;}
        }
        else
        {return false;}
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