package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

public class BotIA extends BotSmart{
    public BotIA(int id) {
        super(id);
    }

    @Override
    public Role processChooseRole(List<Role> toConsiderRoles) {
        if(this.nextPlayer.alreadyChosenRole){
            toConsiderRoles.add(this.dealRoles.getHidden());
        }
        Optional<Role> alt1=this.roleToOptimizeCoins(
            toConsiderRoles
        );
        Optional<Role> alt2= toConsiderRoles.stream().filter(r->r.toString().equals("Architect")).findAny();
        
        
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
    
    
    /**
     * utilise une srrategie pour chercher le quartier le moins cher a poser
     * @return le district a poser
     */
    District whatToBuild(int limit){
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



    @Override
    protected boolean isBuildingFirst() {
        if(getCharacter().toString().equals("Architect")){ //pioche 2 cartes avant de jouer
            return true;
        }
        else if(getCharacter().toString().equals("Thief")){
            return false;
        }
        else if(getCharacter().toString().equals("Warlord")){
            //TODO compléter l'algorithme
            //vérifier si il peut construire
            //si il le peut vérifier la valeur totale qu'aura sa cité
            //comparer avec la valeur totale de la cité de celui qui la cité avec la plus grande valeur
            //si this.city.totalValue()>= other.city.value then true
            //otherwise 
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
    
    
    /**
     * TODO quand on a dans sa main une carte identique à une 
     * de celle de sa cité il est avantageux d'utiliser
     * le labo pour gagner de l'argent avec 
     * puisqu'on a pas le droit de le poser de toute facon
     */
    @Override
    public Optional<District> wantToUseLabo() {
        ArrayList<District> list = hand.cardsAboveGold(getGold());
       		if(!list.isEmpty() && city.getSizeOfCity() >= 6) {
                   District dis = hand.highCostDistrict(getGold());
                   return Optional.of(dis);
            }
            return super.wantToUseLabo();
    }
    
	@Override
	protected boolean isUsingGraveyard(District dis) {
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
    
    
    /**
         * Fonction pour récupérer le Role permettant 
         * d'avoir le plus d'argent
         * On utilisera hidden que si le joueur est le 
         * dernier à choisir son role ie nextPlayer.alreadyChosenRole==true
         */
    /**
	 * Fonction pour récupérer le Role permettant d'avoir le plus d'argent lors de
	 * la collecte d'argent des quartiers On utilisera hidden que si le joueur est
	 * le dernier à choisir son role ie nextPlayer.alreadyChosenRole==true
	 */
	public Optional<Role> roleToOptimizeCoins(List<Role> toConsider) {

		if (city.getSizeOfCity() == 0) {
			// une autre
			return Optional.empty();
		} else {
			ArrayList<String> availableColors = new ArrayList<>();
			toConsider.stream().map(d -> d.getColor()).forEach(s -> {
				if (s.equals("soldatesque") || s.equals("commerce") || s.equals("religion") || s.equals("noblesse")) {
					availableColors.add(s);
				}
			});
			String bestColor = this.city.mostPotentiallyPayingColor(availableColors);

			for (Role r : toConsider) {
				if (r.getColor().equals(bestColor)) {
					return Optional.of(r);
				}
			}
			return Optional.empty();
		}

	}


    
    

    @Override
    public boolean wantToUseFabric() {
        return false;
        
    }

    

    //TODO optimiser par rapport à la couleur
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

    
}