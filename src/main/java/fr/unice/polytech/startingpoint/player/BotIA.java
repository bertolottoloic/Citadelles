package fr.unice.polytech.startingpoint.player;


import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.role.Role;

import java.util.ArrayList;
import java.util.Random;

public class BotIA extends Player{
    private Random random=new Random();

    public BotIA(int id){
        super(id);
    }

    @Override
    public void chooseRole() {
        super.chooseRole();
    }

    @Override
    public void specialMove() {
        targetToKill=pickTargetRole();
        targetToRob=pickTargetRole();
        targetToExchangeHandWith=pickTargetPlayer();
        targetToDestroyDistrict = pickTargetPlayer();
        districtToDestroy = pickRandomDistrict();
        super.specialMove();
    }

    @Override
    protected void action() {
        int i =0;
            ArrayList<District> currHand=new ArrayList<District>(getHand());
            int nb=getCharacter().getNumberDistrictBuildable();
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
                Cela ne pose pas problème puisque le Manager n'est notifié qu'une
                seule fois du fait que le jeu doit prendre fin au lieu de plusieurs
                fois
                */
                support.firePropertyChange("gameOver",gameOver , true);
		        this.gameOver=true;//inutile en fait : c'est là pour le principe
            }
        
    }



    @Override
    public void discard(ArrayList<District> d){
        District discard;
        if(d.size()>=2){
            if(d.get(0).getCost()>getGold()){discard=d.get(0);}
            else if(d.get(1).getCost()>getGold()){discard=d.get(1);}
            else {
                if(d.get(0).getCost()>d.get(1).getCost()){discard=d.get(1);}
                else{discard=d.get(0);}
            }
            d.remove(discard);
            getBoard().getDeck().putbackOne(discard);
        }
        //TODO : Cartes "Merveille" Manufacture, Observatoire, Bibliothèque
    }

    /**
     * Cherche un district qui vaut plus que l'or possédé par le joueur
     * @return true s'il y en a
     */
     boolean highValuedDistrict(ArrayList<District>hand ,int golds) {
    	for(District d : hand){
    		if(d.getValue() > golds) {
    			return true;
    		}
    	}
    	return false;
    }

     District lowCostDistrict(ArrayList<District> hand) {
        District lowCost=hand.get(0);
        for(District d : hand){
            if(d.getCost() < lowCost.getCost()) {
                lowCost=d;
            }
        }
        return lowCost;
    }
    
    @Override
    public boolean coinsOrDistrict() {
        return getGold() < 2 || highValuedDistrict(getHand(),getGold());
    }

    /**
     * Compte le nombre de district dont le cout est plus eleve que le nombre de piece d'or posseder par le joueur
     * @return int : nombre de district
     */

     public int nbTooExpensivesDistricts(ArrayList<District> districts,int golds){
        int n=0;
        for(District d : districts) {
            if (golds < d.getCost()) {
                n++;
            }
        }
        return n;
    }

    /**
     * utilise une srrategie pour chercher le quartier le moins cher a poser
     * @return le district a poser
     */
    District whatToBuild(){
        District lowerCost = lowCostDistrict(getHand());
        if(lowerCost.getCost()<=getGold()){
            return lowerCost;
        }
        else{return null;}
    }

    @Override
    protected boolean isBuildingFirst() {
        if(getCharacter().equals("Wizard")){ //pioche 3 cartes avant de jouer
            return true;}
        else if(getCharacter().equals("Warlord")){ //si la main du magicien est mauvaise active son pouvoir, sinon il construit avant
            int countBadCards=nbTooExpensivesDistricts(getHand(),getGold());
            if(countBadCards>getHand().size()/2){return false;} // si plus de la moitié des cartes sont "mauvaises" active son pouvoir
            else{return true;}
        }
        else
        {return false;}
    }
    
    /**
     * A override en cas de possession de la carte Ecole de Magie
     */
    @Override
    void collectMoneyFromDistricts(){
    	getCity().forEach((District d) -> {
    		if(d.getNom().equals("Ecole de Magie")) {
    			//d.setColor("TODO"); // Le joueur choisit la couleur
    		}
    	});
		super.getCharacter().collectRentMoney();
	}

    Role pickTargetRole(){
        Role character = this.getCharacter();
        ArrayList<Role> roles = this.getBoard().getRoles();
        Role target;
        switch(character.getPosition()){
            case 1:
                target = roles.get(1);
                break;
            case 2:
                target = roles.get(5);
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

    District pickRandomDistrict() {
        ArrayList<District> hand = new ArrayList<District>(getBoard().getPlayers().get(random.nextInt(4)).getCity());
        if(!hand.isEmpty()) {
            District d = hand.get(0);
            for (District d1 : hand) {
                if (d1.getCost() < d.getCost()) d = d1;
            }
            return d;
        }
        return null;
    }
}