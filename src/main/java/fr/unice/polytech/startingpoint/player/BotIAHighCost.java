package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

public class BotIAHighCost extends Player{

    public BotIAHighCost(int id){
        super(id);
    }

   
    @Override
    public Role processChooseRole() {
        ArrayList<Role> leftRoles=this.dealRoles.getLeftRoles();
        String maxColor=hand.bestColorDistrict();
        Role choosenRole=bestRoleToChoose(leftRoles,maxColor);

        return choosenRole;
    }

    //TODO ???
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
    public boolean coinsOrDistrict() {
        return getGold() < 2
                || hand.badCards(getGold()).size()<=hand.size()/2
                || city.getSizeOfCity() >= 6
                || deck.numberOfCards() < 4
                || hand.size()>2;
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
    
    /**
     * TODO stratégie
     */
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



    //TODO utiliser une stratégie concrète
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

    @Override
    public boolean wantToUseFabric() {
        return true;
    }

}