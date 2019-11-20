package fr.unice.polytech.startingpoint.player;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class BotIA extends Player{
    private Random random=new Random();

    public BotIA(int id){
        super(id);
    }

    @Override
    public void chooseRole() {
        Optional<Role> tmpRole=this.roleToOptimizeCoins(
            board.getDealRoles().getLeftRoles(), 
            board.getDealRoles().getHidden()    
        );
        if(tmpRole.isPresent()){
                chooseRole(tmpRole.get());
        }
        else{
            super.chooseRole();
        }

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
        ArrayList<Role> lefts=board.getDealRoles().getLeftRoles();

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
        if(!alreadyChosenRole && board.getDealRoles().getLeftRoles().remove(chosen) ){
                this.setCharacter(chosen);
                alreadyChosenRole=true;
                super.roleInformations();
                if(nextPlayer!=null){
                    nextPlayer.chooseRole();
                }   
        }
    }

    @Override
    public void specialMove() {
        targetToKill=targetToChooseForMurderer();
        targetToRob=targetToChooseForThief();
        targetToExchangeHandWith=pickTargetPlayer();
        targetToDestroyDistrict = pickTargetPlayer();
        districtToDestroy = pickRandomDistrict();
        super.specialMove();
    }

    @Override
    protected void action() {
            if(!getHand().isEmpty()) {
                District toBuild = whatToBuild();
                if (toBuild != null) {
                    addToTheCity(toBuild);
                }
            }

            if(checkFinishBuilding() || getBoard().numberOfCardsOfDeck()<=0){
                /*
                Notez que si il reste encore des cartes dans le deck et
                que le joueur a bien atteint  les 8 districts sans être le premier à
                l'avoir fait, ce bloc n'est pas executé
                Cela ne pose pas problème puisque comme ça le Manager n'est notifié qu'une
                seule fois du fait que le jeu doit prendre fin au lieu de plusieurs
                fois
                */
                support.firePropertyChange("gameOver",gameOver , true);
                this.gameOver=true;//inutile en fait : c'est là pour le principe
            }
        
    }


    /**
     * On garde la carte la moins chere
     */

    @Override
    public void discard(ArrayList<District> d){
        if(d.size()>=2){
            d.sort((a,b)->
                Integer.compare(a.getCost(),b.getCost())
            );
            while(d.size()>1){//On ne garde qu'une carte
                getBoard().getDeck().putbackOne(d.remove(d.size()-1));
            }
        }
    }

    @Override
    public boolean coinsOrDistrict() {
    	return getGold() < 2 
    			|| hand.highValuedDistrict(getGold())
    			|| city.getSizeOfCity() == 7
    			|| board.getDeck().numberOfCards() < 4;
    }
    
    /**
     * utilise une srrategie pour chercher le quartier le moins cher a poser
     * @return le district a poser
     */
    District whatToBuild(){
        District lowerCost = hand.lowCostDistrict();
        if(lowerCost.getCost()<=getGold()){
            return lowerCost;
        }
        else{return null;}
    }

    @Override
    protected boolean isBuildingFirst() {
        if(getCharacter().toString().equals("Wizard")){ //pioche 3 cartes avant de jouer
            return true;}
        else if(getCharacter().toString().equals("Warlord")){ //si la main du magicien est mauvaise active son pouvoir, sinon il construit avant
            int countBadCards=getHand().nbTooExpensiveDistricts(getGold());
            if(countBadCards>getHand().size()/2){return false;} // si plus de la moitié des cartes sont "mauvaises" active son pouvoir
            else{return true;}
        }
        else
        {return false;}
    }
    
    @Override
	protected boolean isUsingFabric() {
    	return hand.isEmpty()
    			&& getGold() >= 5 
    			&& city.getSizeOfCity() < 7
    			&& hand.nbTooExpensiveDistricts(getGold()) == getHand().size();
    }
    
    /**
     * A override en cas de possession de la carte Ecole de Magie
     */
   /* @Override
    void collectMoneyFromDistricts(){
    	getCity().forEach((District d) -> {
    		if(d.getNom().equals("Ecole de Magie")) {
    			//d.setColor("TODO"); // Le joueur choisit la couleur
    		}
    	});
		super.getCharacter().collectRentMoney();
    }*/
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

    Role pickTargetRole(){
        Role target;
        switch(this.getCharacter().getPosition()){
            case 1:
                target = targetToChooseForMurderer();
                break;
            case 2:
                target = board.getRole(5);
                break;
            default :
                target = null;
                break;
        }
        return target;
    }
    
    Player pickTargetPlayer(){
        Role character = this.getCharacter();
        Player target;
        switch(character.getPosition()){
            case 3:
                target = this.getBoard().getPlayerWithTheBiggestHand();
                break;
            case 8:
                target = this.getBoard().getPlayerWithTheBiggestCity();
                break;
            default :
                target = null;
                break;
        }
        return target;
    }

    //TODO
    District pickRandomDistrict() {
        ArrayList<District> hand = new ArrayList<District>(getBoard().getPlayers().get(random.nextInt(4)).getCity().getListDistricts());
        if(!hand.isEmpty()) {
            District d = hand.get(0);
            for (District d1 : hand) {
                if (d1.getCost() < d.getCost()) d = d1;
            }
            return d;
        }
        return null;
    }

    Role targetToChooseForMurderer(){
	    Role target;
	    if(hidden!=null){
	        switch(hidden.getPosition()) {
                case 7:
                    target = board.getRole(5);
                    break;
                default:
                    target = board.getRole(6);
                    break;
            }
        }
	    return board.getRole(6);
    }

    Role targetToChooseForThief(){
	    if(unknownRole.size()==2 && board.getCrown().getCrownOwner().getGold()>2){
	        if(unknownRole.get(0)==board.getRole(0)) return unknownRole.get(1);
	        return unknownRole.get(0);
        }
	    Random r = new Random();
	    return board.getRole(r.nextInt(knownRole.size()));
    }
}