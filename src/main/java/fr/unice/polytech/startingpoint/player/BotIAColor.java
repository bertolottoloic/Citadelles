package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.List;

public class BotIAColor extends Player{
    public BotIAColor(int id){
        super(id);
    }

    @Override
    public List<fr.unice.polytech.startingpoint.board.District> processWhatToBuild() {
        // TODO Auto-generated method stub
        return List.of(this.whatToBuild(this.getGold()));
    }
    District whatToBuild(int limit){
        District district = getHand().highCostDistrict(limit);
        if(district.getCost()<=limit){
            return district;
        }
        else{
            return null;
        }
    }

    @Override
    public void discard(List<District> d){
        if(d.size()>=2){
            d.sort((a,b)->
                    Integer.compare(a.getCost(),b.getCost())
            );
            while(d.size()>1){//On ne garde qu'une carte
                if(d.get(0).getCost()>getGold()){
                    this.deck.putbackOne(d.remove(0));
                }
                else{
                    this.deck.putbackOne(d.remove(d.size()-1));
                }
            }
        }
    }


    @Override
    public boolean coinsOrDistrict() {
        return getGold() < 2
                || hand.badCards(getGold()).size()<=hand.size()/2
                || city.getSizeOfCity() >= 6
                || deck.numberOfCards() < 4
                || hand.size()>2;
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


    Role pickTargetRole(){
        Role character = this.getCharacter();
        Role target;
        switch(character.getPosition()){
            case 1:
                target = this.dealRoles.getRole("Thief");
                break;
            case 2:
                target = this.dealRoles.getRole("Merchant");
                break;
            default :
                target = null;
                break;
        }
        return target;
    }

    @Override
    protected boolean isBuildingFirst() {
        if(getCharacter().toString().equals("Architect")){ //pioche 2 cartes avant de jouer
            return false;
        }
        else if(getCharacter().toString().equals("Wizard")){ //si la main du magicien est mauvaise active son pouvoir, sinon il construit avant
            int countBadCards=getHand().badCards(getGold()).size();
            if(countBadCards>getHand().size()/2){
                return false;
            } // si plus de la moitié des cartes sont "mauvaises" active son pouvoir
            else{
                return true;
            }
        }
        else {
            return true;
        }
    }

}
