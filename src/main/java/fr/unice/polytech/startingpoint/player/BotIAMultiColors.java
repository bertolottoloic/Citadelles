package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BotIAMultiColors extends Player{
    public BotIAMultiColors(int id){
        super(id);
    }


    @Override
    public Role processChooseRole() {
        ArrayList<Role> leftRoles=this.dealRoles.getLeftRoles();
        String maxColor=hand.bestColorDistrict();
        Role choosenRole=bestRoleToChoose(leftRoles,maxColor);

        return choosenRole;
    }
    Role bestRoleToChoose(ArrayList<Role> roles, String color){
        Optional<Role> optWizard=roles.stream().filter(r->r.toString().equals("Architect")).findAny();
        if(optWizard.isPresent()){
            return optWizard.get();
        }
        optWizard=roles.stream().filter(r->r.toString().equals("Thief")).findAny();
        if(optWizard.isPresent()){
            return optWizard.get();
        }
        optWizard=roles.stream().filter(r->r.toString().equals("Wizard")).findAny();

        if(hand.badCards(getGold()).size()>hand.size()/2&& optWizard.isPresent()){
            return optWizard.get();
        }
        int position;
        switch (color){
            case "religion": position=5;
                break;
            case "soldatesque": position =8;
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

    District whatToBuild(int limit){
        ArrayList<DistrictColor> missingColors = MissingColors();
        if(missingColors.size()==0){
            return hand.highCostDistrict(limit);
        }
        else {
            ArrayList<District> districts = new ArrayList<District>();
            for (District d : hand.toList()) {
                if(missingColors.contains(d.primaryColor())&&d.getCost()<=limit) {
                    districts.add(d);
                }
            }
            districts.sort((a,b)->
                    Integer.compare(a.getCost(),b.getCost()));
            return(districts.size()>1)?districts.get(districts.size()-1):hand.lowCostDistrict();
        }
    }

    ArrayList<DistrictColor> MissingColors(){
        HashMap<DistrictColor,Integer> countColors=hand.countColors();
        ArrayList<DistrictColor> missingColors = new ArrayList<>();
        for(DistrictColor key : countColors.keySet()){
            if(countColors.get(key)==0){
                missingColors.add(key);
            }
        }
        return missingColors;
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

    @Override
    public Optional<District> wantToUseLabo() {
        ArrayList<District> list = hand.cardsAboveGold(getGold());
        if(!list.isEmpty()
                && city.getSizeOfCity() >= 6) {
            District dis = hand.lowCostDistrict();
            if(!list.contains(dis)) {
                return Optional.of(dis);
            }
        }
        return Optional.empty();
    }


    @Override
    public boolean wantToUseFabric() {
        // TODO Auto-generated method stub
        return super.wantToUseFabric();
    }

}
