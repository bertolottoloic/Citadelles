package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BotRainbow extends BotSmart{
    public BotRainbow(int id){
        super(id);
    }

    /*-------------------------OVERRIDING --------------------------------------------------------*/

    @Override
    public Role processChooseRole(List<Role> toConsiderRoles) {
        String maxColor=hand.bestColorDistrict();       
        return bestRoleToChoose(toConsiderRoles,maxColor);
    }
    
    @Override
    public List<District> processWhatToBuild() {
    	List<District> specialBuilding = super.processWhatToBuild();
    	if(super.processWhatToBuild().isEmpty()) {
    		District tmp=this.whatToBuild(this.getGold());
            if(tmp!=null){
                return new ArrayList<>(List.of(tmp));
            }
    	}
    	return specialBuilding;
    }
    
	@Override
	public void discard(List<District> d) {
		d.sort((a, b) -> Integer.compare(a.getCost(), b.getCost()));
		ArrayList<String> missingColors = new ArrayList<>();
		this.missingColors().forEach(c -> missingColors.add(c.name()));
		while (d.size() > this.getCharacter().getNumberDistrictKeepable()) {// On ne garde qu'une carte
			if (d.get(0).getCost() > getGold() || !missingColors.contains(d.get(0).getColorsList().get(0))) {
				this.deck.putbackOne(d.remove(0));
			} else {
				this.deck.putbackOne(d.remove(d.size() - 1));
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

    @Override
    public Optional<District> wantsToUseLabo() {
    	List<District> districts = hand.discardDistrictsForMultiColors();
    	if(districts.isEmpty()) {
    		District dis = hand.lowCostDistrict();
    		if(dis == null) {
    	        return Optional.empty();
    		} else if(dis.getValue() < getGold()) {
                return Optional.of(dis);
    		}
        } else {
        	return Optional.of(districts.get(0));
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
    public boolean wantsToUseFabric() {
        return !city.containsAllColors() ||
        		(getGold() >= 8	&& !hand.highValuedDistrict(getGold()-3));
    }
    
    /*------------------------------------------------------------------------------*/

    District whatToBuild(int limit){//TODO test
        List<DistrictColor> missingColors = missingColors();
        if(missingColors.isEmpty()){
            return hand.highCostDistrict(limit);
        }
        else {
            List<District> districts = new ArrayList<>();
            for (District d : hand.toList()) {
                if(missingColors.contains(d.primaryColor())&&d.getCost()<=limit) {
                    districts.add(d);
                }
                if(d.getName().equals("Cimetiere") && limit <= 5 && !getCharacter().equals("Warlord")
                		|| d.getName().equals("Cour des Miracles") && limit <= 2 && gameOver == false
                		|| d.getName().equals("Manufacture") && limit <= 8
                		|| d.getName().equals("Laboratoire") && limit <= 6 && !hand.discardDistrictsForMultiColors().isEmpty()) {
                	return d;
                }
            }
            districts.sort((a,b)->
                    Integer.compare(a.getCost(),b.getCost()));
            return(districts.size()>1)?districts.get(districts.size()-1):hand.lowCostDistrict();
        }
    }
    
    ArrayList<DistrictColor> missingColors(){
        HashMap<DistrictColor,Integer> countColors=hand.countColors();
        HashMap<DistrictColor,Integer> countColorsC=city.countColors();
        countColorsC.forEach((k,v) ->{
            if(countColors.get(k)<v)
                countColors.put(k,v);
        });
        ArrayList<DistrictColor> missingColors = new ArrayList<>();
        for(DistrictColor key : countColors.keySet()){
            if(countColors.get(key)==0){
                missingColors.add(key);
            }
        }

        return missingColors;
    }
}
