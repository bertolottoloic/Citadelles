package fr.unice.polytech.startingpoint.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

public class BotIA extends Player{
    public BotIA(int id) {
        super(id);
    }

    

    @Override
    public Role processChooseRole() {
        List<Role> toConsider=new ArrayList<>(this.dealRoles.getLeftRoles());
        // TODO Auto-generated method stub
        if(this.nextPlayer.alreadyChosenRole){
            toConsider.add(this.dealRoles.getHidden());
        }
        Optional<Role> alt1=this.roleToOptimizeCoins(
            toConsider
        );
        Optional<Role> alt2= toConsider.stream().filter(r->r.toString().equals("Architect")).findAny();
        
        
        Optional<Role> alt3= toConsider.stream().filter(r->r.toString().equals("Merchant")).findAny();
        Optional<Role> alt4= toConsider.stream().filter(r->r.toString().equals("Thief")).findAny();
        Optional<Role> alt5= toConsider.stream().filter(r->r.toString().equals("Murderer")).findAny();
        Optional<Role> alt6= toConsider.stream().filter(r->r.toString().equals("Wizard")).findAny();

        this.attributeProbsToPlayer();
        
        return alt1
        .or(()->alt2)
        .or(()->alt3)
        .or(()->alt4)
        .or(()->alt5)
        .or(()->alt6)
        .or(()->Optional.of(super.processChooseRole())).get();
        
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
     */

    public void attributeProbsToPlayer(){
        ArrayList<Player> pl=board.getPlayers();
        ArrayList<Role> toConsider=new ArrayList<>(this.dealRoles.getLeftRoles());
        var hiddenRole=this.dealRoles.getHidden();
        ArrayList<Role> visibles=this.dealRoles.getVisible();

        if(this.nextPlayer!=null && this.nextPlayer.alreadyChosenRole){
            toConsider.add(hiddenRole);
        }
        for (Player player : pl) {
            //tous ceux qui ont déja choisi leurs roles n'ont évidemment pas 
            //pu prendre ce qui reste
            if(player.alreadyChosenRole){
                for (Role role : toConsider) {
                    matches.setProbability(player.getId(), role.toString(), 0);
                }
            }
            //personne n'a les roles visbibles
            for (Role role : visibles) {
                matches.setProbability(player.getId(), role.toString(), 0);
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
        //if(d.size()>=2){
            d.sort((a,b)->
                Integer.compare(a.getCost(),b.getCost())
            );
            while(d.size()>this.getCharacter().getNumberDistrictKeepable()){
                this.deck.putbackOne(d.remove(d.size()-1));
                
            }
        //}
    }

    //TODO tests
	public Tmp buildables(List<District> toConsider,int limit){
        if(toConsider.isEmpty() || limit<=0){
            return new Tmp(0,new ArrayList<District>());
        }
        else if(toConsider.get(0).getCost()>limit){
            //Explore right branch only
            return buildables(toConsider.subList(1,toConsider.size()), limit);
        }
        else{
            var nextItem=toConsider.get(0);
            //Explore left branch
            Tmp resultLeft=buildables(toConsider.subList(1, toConsider.size()), limit-nextItem.getCost());
            resultLeft.addVal(nextItem.getValue());
            //Explore right branch
            Tmp resultRight=buildables(toConsider.subList(1, toConsider.size()), limit);

            if(resultLeft.getVal()>resultRight.getVal()){
                resultLeft.getDistricts().add(nextItem);
                
                return resultLeft;
            }
            else{
                return resultRight;
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
        this.attributeProbsToPlayer();
        Set<String> targets = matches.possibleRolesFor(this.board.playerWithTheBiggestCity(this).getId());
        targets.remove(this.getCharacter().toString());//on exclut son propre role
        return this.dealRoles.getRole(targets.stream().findFirst().get());
    }
    @Override
    public Role processWhoToRob() {
        Set<String> targets = matches.possibleRolesFor(board.richestPlayer(this).getId());
        targets.remove(this.getCharacter().toString());//on exclut son propre role
        //TODO exclure celui qui a deja ete tué
        /*Optional<Role> optKilled=dealRoles.roleKilled();
        if(optKilled.isPresent()){
            targets.remove(optKilled.get().toString());
        }*/
        
        return this.dealRoles.getRole(targets.stream().findFirst().get());
    }

    @Override
    public boolean wantToUseFabric() {
        return false;
        
    }

    @Override
    public boolean coinsOrDistrict() {
        return getGold() < 8;
    }

    //TODO optimiser par rapport à la couleur
    @Override
    public List<District> processWhatToBuild() {
        //District tmp=this.whatToBuild(this.getGold());
        List<District> toConsider;
        if(city.getSizeOfCity()==7){//si on est sur le point de finir 
            //on peut construire des cartes de cout 1 puisque le condotierre ne peut pas détruire
            //les cités finies
            //TODO trier le résultat final par ordre décroissant de valeur
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
            .limit(this.getCharacter().getNumberDistrictBuildable())
            .collect(Collectors.toList());
    }

    
}