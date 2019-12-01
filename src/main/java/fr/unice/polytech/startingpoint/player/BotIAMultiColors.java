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

    @Override
    public List<District> processWhatToBuild() {
        District tmp=this.whatToBuild(this.getGold());
        if(tmp!=null){
            return List.of(tmp);
        }

        return new ArrayList<>();
        // TODO Auto-generated method stub
    }

    District whatToBuild(int limit){
        ArrayList<DistrictColor> missingColors = missingColors();
        if(missingColors.isEmpty()){
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

    ArrayList<DistrictColor> missingColors(){
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
    public boolean wantsToUseFabric() {//TODO Test
        return !city.containsAllColors() ||
        		(getGold() >= 8	&& !hand.highValuedDistrict(getGold()-3));
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


    /*@Override
    public Optional<District> wantsToUseLabo() { //TODO Test
    	List<District> districts = hand.discardDistrictsForMultiColors();
    	District anotherDis = districts.get(0);
    	if(anotherDis == null) {
    		District dis = hand.lowCostDistrict();
    		if(dis == null) {
    	        return Optional.empty();
    		} else if(dis.getValue() < getGold()) {
                return Optional.of(dis);
    		}
        } else {
        	return Optional.of(anotherDis);
        }
		return Optional.empty();
    }*/

    @Override
    public Optional<District> wantsToUseLabo() { //TODO Test
        District dis = hand.lowCostDistrict();
        if(dis == null) {
            return Optional.empty();
        } else if(dis.getValue() < getGold()) {
            return Optional.of(dis);
        }
        return Optional.empty();
    }
    
    @Override
	protected boolean wantsToUseGraveyard(District dis) {
		if (city.containsWonder("Cimetiere")) {
			if (dis != null && !getCharacter().toString().equals("Warlord")) {
				if (missingColors().contains(dis.primaryColor()) ||
						dis.getCost() < getGold() && dis.getValue() > 4) {
					return true;
				}
			}
		}
		return false;
	}

    @Override
    public Role processWhoToKill() {
        return this.dealRoles.getRole("Thief");
    }

    @Override
    public Role processWhoToRob() {
        // TODO Auto-generated method stub
        return this.dealRoles.getRole("Merchant");
    }

    @Override
    public Player processWhoToExchangeHandWith() {
        return this.board.playerWithTheBiggestHand(this);
    }

    @Override
    public Player processWhoseDistrictToDestroy() {
        return this.board.playerWithTheBiggestCity(this);
    }

    @Override
    public District processDistrictToDestroy(Player target) {
        return pickRandomDistrict();
    }


}
