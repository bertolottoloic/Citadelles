package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;
/**
 * @author Patrick Anagonou 
 * @author Heba Bouzidi
 * @author Loic Bertolotto
 * @author Clement Poueyto
 *
 * */
public class BotBuildFast extends BotSmart{
    public BotBuildFast(int id) {
        super(id);
        
    }
    
    /* -----------------------OVERRIDING ------------------------------------*/
    
    @Override
    public List<District> processWhatToBuild() {
        //District tmp=this.whatToBuild(this.getGold());
        List<District> toConsider;
        if(city.getSizeOfCity()==7){//si on est sur le point de finir 
            //on peut construire des cartes de cout 1 puisque le condotierre
            // ne peut pas détruire
            //les cités finies
            toConsider=getHand().toList().stream()
            .filter(d->!city.alreadyContains(d))
            .collect(Collectors.toList());
        }
        else{//ne pas construire des cartes de cout 1 sinon le condotierre peut les détruire facilement
            //sans rien payer
            toConsider=getHand().toList().stream()
            .filter(d->!city.alreadyContains(d)&& d.getCost()>1)
            .collect(Collectors.toList());
        }
        return buildables(
                        toConsider,
                        getGold()
                )
            .getDistricts().stream()
            .sorted((a,b)->-Integer.compare(a.getValue(), b.getValue()))
            .collect(Collectors.toList());
    }
    
    @Override
    public Player processWhoseDistrictToDestroy() {
        return board.playerWithTheBiggestCity(this);
    }

    

    @Override
    public District processDistrictToDestroy(Player target) {
        Optional<District> tmp=target.city.cheaperDistrict();
        if(tmp.isPresent()){
            return tmp.get();
        }
        else{
            return null;
        }
    }
    
    @Override
    public Role processWhoToKill() {
        this.attributeProbsToPlayer();
        Set<String> targets = matches.possibleRolesFor(this.board.playerWithTheBiggestCity(this).getId());
        targets.remove(this.getCharacter().toString());//on exclut son propre role
        return this.dealRoles.getRole(targets.stream().findFirst().get());
    }
    @Override
    public Role processWhoToRob() {
        Set<String> targets = matches.possibleRolesFor(board.richestPlayer(this).getId());
        targets.remove(this.getCharacter().toString());//on exclut son propre role
        /*Optional<Role> optKilled=dealRoles.roleKilled();
        if(optKilled.isPresent()){
            targets.remove(optKilled.get().toString());
        }
        */
        return this.dealRoles.getRole(targets.stream().findFirst().get());
    }
    
    @Override
    public Role processChooseRole(List<Role> toConsiderRoles) {
        Optional<Role> alt1= toConsiderRoles.stream().filter(r->r.toString().equals("Architect")).findAny();
        Optional<Role> alt2=this.roleToOptimizeCoins(
            toConsiderRoles
        );
        
        
        
        Optional<Role> alt3= toConsiderRoles.stream().filter(r->r.toString().equals("Merchant")).findAny();
        Optional<Role> alt4= toConsiderRoles.stream().filter(r->r.toString().equals("Thief")).findAny();
        Optional<Role> alt5= toConsiderRoles.stream().filter(r->r.toString().equals("Murderer")).findAny();
        Optional<Role> alt6= toConsiderRoles.stream().filter(r->r.toString().equals("Wizard")).findAny();

        this.attributeProbsToPlayer();
        
        return alt1
        .or(()->alt2)
        .or(()->alt3)
        .or(()->alt4)
        .or(()->alt5)
        .or(()->alt6)
        .or(()->Optional.of(super.processChooseRole(toConsiderRoles))).get();
    }
    
    @Override
    protected boolean isBuildingFirst() {
        if(getCharacter().toString().equals("Architect")){ //pioche 2 cartes avant de jouer
            return true;
        }
        else if(getCharacter().toString().equals("Thief")){
            return false;
        }
        else if(getCharacter().toString().equals("Warlord")){
            return true;
        }
        else if(getCharacter().toString().equals("Wizard")){//si la main du magicien est mauvaise active son pouvoir, sinon il construit avant
            int countBadCards=getHand().nbTooExpensiveDistricts(getGold());
            if(countBadCards==getHand().size()){
                return false;
            }
            else{
                return true;
            }
        }
        else {
            return true;
        }
    }
    
    @Override
    public boolean coinsOrDistrict() {
        return getGold() < 5;

    }
    
    /**
     *quand on a dans sa main une carte identique à une
     * de celle de sa cité il est avantageux d'utiliser
     * le labo pour gagner de l'argent avec 
     * puisqu'on a pas le droit de le poser de toute facon
     */
    @Override
    public Optional<District> wantsToUseLabo() {
        ArrayList<District> list = hand.cardsAboveGold(getGold());
       		if(!list.isEmpty() && city.getSizeOfCity() >= 6) {
                   District dis = hand.highCostDistrict(getGold());
                   return Optional.of(dis);
            }
            return super.wantsToUseLabo();
    }
    
	@Override
	protected boolean wantsToUseGraveyard(District dis) {
		if (city.containsWonder("Cimetiere")) {
			if (dis != null && !getCharacter().toString().equals("Warlord")) {
				District lowest = hand.lowCostDistrict();
				if(lowest == null) {
					if(dis.getCost() < getGold()) {
	       				return true;
					}
				} else {
					if ((lowest.getCost() < getGold() 
							|| dis.getCost() < getGold()) 
							&& lowest.getCost() != dis.getCost()) {
	       				return true;
					}
				}
			}
		}
		return false;
	}
    
	@Override
    public boolean wantsToUseFabric() {
        return getGold() >= 5
    			&& city.getSizeOfCity() < 7;
    }

    @Override
    public ArrayList<District> badCards(){
        ArrayList<District> badCards = new ArrayList<>();
        hand.toList().forEach(d -> {
            if(d.getCost()>3){
                badCards.add(d);
            }
        });
        return badCards;
    }
	/*-------------------------------------------------------------*/
    
    /**
     * utilise une srrategie pour chercher le quartier le moins cher a poser
     * @return le district a poser
     */
    District whatToBuild(int limit){
    	if (handHasTheDistrict("Donjon") && !cityHasTheDistrict("Donjon") && 3 <= limit) {
    		return hand.findDistrictByName("Donjon");
    	}
    	if (handHasTheDistrict("Laboratoire") && 6 <= limit && getHand().highCostDistrict(getGold()) != null) {
			return hand.findDistrictByName("Laboratoire");
		}
            if(getCharacter().toString().equals("Architect")) {
                District lowerCost = hand.lowCostDistrict();
                if (lowerCost.getCost() <= limit) {
                    return lowerCost;
                } else {
                    return null;
                }
            }
            else{
                District lowerCost = hand.lowCostDistrictForNextTurn(getGold()); //prend le district le plus cher de manière à avoir assez de golds pour le tour suivant
                if(lowerCost.getCost()<=limit){
                    return lowerCost;
                }
                else{
                    return null;
                }
            }
    }
    
    /**
         * Fonction pour récupérer le Role permettant 
         * d'avoir le plus d'argent
         * On utilisera hidden que si le joueur est le 
         * dernier à choisir son role ie nextPlayer.alreadyChosenRole==true
         */
    
}