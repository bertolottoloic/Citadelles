package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.board.DistrictColor;
import fr.unice.polytech.startingpoint.role.Role;

/**
 * @author Patrick Anagonou 
 * @author Heba Bouzidi
 * @author Loic Bertolotto
 * @author Clement Poueyto
 *
 * */

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
        int gold=this.getGold();
        var newDistricts=buildNewColors(gold);
        if(city.containsAllColors() || newDistricts.size()==0){
            return super.processWhatToBuild();
        }
        else{
            newDistricts.sort((a,b)->-Integer.compare(a.getValue(), b.getValue()));
            if(newDistricts.size()<this.getCharacter().getNumberDistrictBuildable()){
                var smartBuild=new HashSet<District>(super.processWhatToBuild());
                smartBuild.addAll(newDistricts);
                return new ArrayList<>(smartBuild);
                
            }
            

            return newDistricts;
        }
    }

    
    @Override
    public void discard(List<District> d) {
        d.sort((a,b)->
                Integer.compare(a.getCost(),b.getCost())
        );
        ArrayList<DistrictColor> missingColors = new ArrayList<>();
        this.missingColors().forEach(c -> missingColors.add(c));
        int tmp=this.getCharacter().getNumberDistrictKeepable();
        //vérifier si on a déja les 5 couleurs
        if(city.containsAllColors()){
            while(d.size()>tmp){
                if(d.get(0).getCost()>getGold()){
                    this.deck.putbackOne(d.remove(0));
                } else {
                    this.deck.putbackOne(d.remove(d.size() - 1));
                }
            }
        }
        else{
            //chercher les districts dont j'ai déja la couleur
            //vérifier
            Iterator<District> it=d.iterator();
            
            while(it.hasNext() && d.size()>tmp){
                District present= it.next();
                if(!missingColors.contains(present.primaryColor())){
                    it.remove();
                }
            }
            while(d.size()>tmp){
                if(d.get(0).getCost()>getGold()){
                    this.deck.putbackOne(d.remove(0));
                } else {
                    this.deck.putbackOne(d.remove(d.size() - 1));
                }
            }
        }
    }
    
	
    
    @Override
    public boolean coinsOrDistrict() {
        return getGold() < 4
                || hand.badCards(getGold()).size()<hand.size()/2
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

    District whatToBuild(int limit){
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
                if(d.getName().equals("Cimetiere") && limit <= 5 && !getCharacter().toString().equals("Warlord")
                		|| d.getName().equals("Cour des Miracles") && limit <= 2
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

    public List<District> buildNewColors(int limit){
        if(city.containsAllColors()){
            return List.of();
        }
        Set <DistrictColor> tmpHand=hand.colorsOfHand();
        Set <DistrictColor> tmpCity=city.colorsOfCity();
        tmpHand.removeAll(tmpCity);

        if(tmpHand.size()==0){
            //si il n'y a pas dans la main des couleurs non construites
            return List.of();
        }
        else{
            var toConsider= hand.toList().stream().filter(d->tmpHand.contains(d.primaryColor()))
            .collect(Collectors.toList());

            return buildables(toConsider, limit).getDistricts();
        }
    }

    
}
