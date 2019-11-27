package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BotIA extends Player{
    public BotIA(int id) {
        super(id);
    }

    

    @Override
    public Role processChooseRole() {
        // TODO Auto-generated method stub
        Optional<Role> alt1= this.dealRoles.getLeftRoles().stream().filter(r->r.toString().equals("Architect")).findAny();
        
        Optional<Role> alt2=this.roleToOptimizeCoins(
            this.dealRoles.getLeftRoles(), 
            this.dealRoles.getHidden()    
        );
        
        return alt1.or(()->alt2).or(()->Optional.of(super.processChooseRole())).get();
        
    }

    /**
     * Cette fonction permet d'attribuer
     * des probabilités pour permettre au joueur 
     * de deviner le rôle qu'ont les autres joueurs
     *  Ex: Au cours de la distribution
     *  si on a a choisir parmi des personnages (p1,p2,p3)
     * Cela veut dire que tous les joueurs ayant choisi
     * avant n'ont pas pris (p1,p2,p3) d'où
     * la probabilité pour qu'ils aient (p1,p2 ou p3)
     * comme role est 0
     * 
     * TODO : une méthode similaire pour les joueurs suivants
     */

    public void attributeProbsToPreviousPlayer(){
        ArrayList<Player> pl=board.getPlayers();
        ArrayList<Role> lefts=this.dealRoles.getLeftRoles();

        for (Player player : pl) {
            if(player.alreadyChosenRole){
                for (Role role : lefts) {
                    matches.setProbability(player.getId(), role.toString(), 0);
                }
            }
            
        }
    }
    /**
     * Fonction qui modifie choisit pour le
     * joueur le Role passé en paramètre doit être modifié quand il faudra
     * utiliser le Role caché
     * @param chosen
     */
    public void chooseRole(Role chosen){
        if(!alreadyChosenRole && this.dealRoles.getLeftRoles().remove(chosen) ){
                this.setCharacter(chosen);
                alreadyChosenRole=true;
                //super.roleInformations();
                if(nextPlayer!=null){
                    nextPlayer.chooseRole();
                }   
        }
    }

    
    /**
     * On garde la carte la moins chere
     */
    @Override
    public void discard(List<District> d){
        if(d.size()>=2){
            d.sort((a,b)->
                Integer.compare(a.getCost(),b.getCost())
            );
            while(d.size()>this.getCharacter().getNumberDistrictKeepable()){
                this.deck.putbackOne(d.remove(d.size()-1));
            }
        }
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
        // TODO Auto-generated method stub
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
	 * le dernier à choisir son role ie nextPlayer.alreadyChosenRole==true TODO
	 * utliser le parametre hidden
	 */
	public Optional<Role> roleToOptimizeCoins(ArrayList<Role> lefts, Role hidden) {

		if (city.getSizeOfCity() == 0) {
			// une autre
			return Optional.empty();
		} else {
			ArrayList<String> availableColors = new ArrayList<>();
			lefts.stream().map(d -> d.getColor()).forEach(s -> {
				if (s.equals("soldatesque") || s.equals("commerce") || s.equals("religion") || s.equals("noblesse")) {
					availableColors.add(s);
				}
			});
			String bestColor = this.city.mostPotentiallyPayingColor(availableColors);

			for (Role r : lefts) {
				if (r.getColor().equals(bestColor)) {
					return Optional.of(r);
				}
			}
			return Optional.empty();
		}

	}


    @Override
    public Player processWhoseDistrictToDestroy() {
        return board.playerWithTheBiggestCity(this);
    }

    @Override
    public Player processWhoToExchangeHandWith() {
        if(Math.abs(this.hand.size()-this.hand.badCards(this.getGold()).size())>=(this.hand.size()/2)){
            this.setCardsToExchange(this.hand.badCards(this.getGold()));
            return null;
        } 
        if(this.getBoard().playerWithTheBiggestHand(this).getHand().size()>=this.hand.size()){
            return this.getBoard().playerWithTheBiggestHand(this); 
        }
        this.setCardsToExchange(new ArrayList<District>(this.hand.toList()));
        return null;
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
        Set<String> targets = matches.possibleRolesFor(this.board.playerWithTheBiggestCity(this).getId());
        return this.dealRoles.getRole(targets.stream().findFirst().get());
    }
    @Override
    public Role processWhoToRob() {
        Set<String> targets = matches.possibleRolesFor(board.richestPlayer(this).getId());
        return this.dealRoles.getRole(targets.stream().findFirst().get());
    }

    @Override
    public boolean wantToUseFabric() {
        // TODO Auto-generated method stub
        return super.wantToUseFabric();
    }

    @Override
    public boolean coinsOrDistrict() {
        return getGold() < 2
                || hand.badCards(getGold()).size()<=hand.size()/2
                || city.getSizeOfCity() >= 6
                || this.deck.numberOfCards() < 4
                || hand.size()>2;
    }

    @Override
    public List<District> processWhatToBuild() {
        District tmp=this.whatToBuild(this.getGold());
        if(tmp!=null){
            return List.of(tmp);
        }

        return new ArrayList<>();
        
    }

}