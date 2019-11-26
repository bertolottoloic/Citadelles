package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.Optional;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

public class BotIAHighCost extends Player {

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
    public void specialMove() {
        targetToKill=pickTargetRole();
        targetToRob=pickTargetRole();
        targetToExchangeHandWith=this.board.playerWithTheBiggestHand(this);
        targetToDestroyDistrict = this.board.playerWithTheBiggestCity(this);
        districtToDestroy = pickRandomDistrict();
        super.specialMove();
    }

    @Override
    protected void action() {
        if(!getHand().isEmpty()) {
            District toBuild = whatToBuild(getGold());
            if (toBuild != null) {
                addToTheCity(toBuild);
            }
        }

        if(checkFinishBuilding() || this.deck.numberOfCards()<=0){
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
    public void discard(ArrayList<District> d){
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
                || hand.nbBadCards(getGold())<=hand.size()/2
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
            int countBadCards=getHand().nbBadCards(getGold());
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
       		
   
    
    
    District findDestroyedDistrict() {
    	ArrayList<Player> players = board.getPlayers();
    	for(Player p : players) {
    		District tmp = p.destroyedDistrict;
    		if(tmp != null) {
    			return tmp;
    		}
    	}
    	return null;
	}

	@Override
	protected void isUsingGraveyard() {
		if (city.containsWonder("Cimetiere")) {
			District dis = findDestroyedDistrict();
			if (dis != null && !getCharacter().toString().equals("Warlord")) {
				if (dis.getCost() < getGold() && dis.getValue() > 4) {
					System.out.println("Joueur " + getId() + " possède et peut utiliser le cimetière");
					bank.deposit(1, this);
					hand.add(dis);
				}
			}
		}
	}

    //
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
        // TODO Auto-generated method stub
        return super.wantToUseFabric();
    }

}