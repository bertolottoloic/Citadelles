package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

public class BotSpender extends BotSmart {

    public BotSpender(int id) {
        super(id);
    }

   
    

    @Override
    public Role processChooseRole(List<Role> toConsiderRoles) {
        String maxColor=hand.bestColorDistrict();
        Role choosenRole=bestRoleToChoose(toConsiderRoles,maxColor);
        
        return choosenRole;
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
    public List<District> processWhatToBuild() {
        District tmp=this.whatToBuild(this.getGold());
        if(tmp!=null){
            return new ArrayList<District>(List.of(tmp));
        }

        return new ArrayList<>();
        // TODO Auto-generated method stub
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
            d.sort((a,b)->
                    Integer.compare(a.getCost(),b.getCost())
            );
            while(d.size()>this.getCharacter().getNumberDistrictKeepable()){//On ne garde qu'une carte
                if(d.get(0).getCost()>getGold()){
                    this.deck.putbackOne(d.remove(0));
                }
                else{
                    this.deck.putbackOne(d.remove(d.size()-1));
                }
            }
    }

    @Override
    public boolean coinsOrDistrict() {//TODO Test
        return getGold() < 2
                || hand.badCards(getGold()).size()<=hand.size()/2;
    }


    /**
     * Strategy : get rid of the lowest valued district
     */
    @Override
    public Optional<District> wantsToUseLabo() {
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
				if (dis.getCost() < getGold() && dis.getValue() > 4) {
					return true;
				}
			}
		}
		return false;
	}



    

    @Override
    public boolean wantsToUseFabric() {
        return getGold() >= 8
    			&& !hand.highValuedDistrict(getGold()-3);
    }

}