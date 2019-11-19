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

    

     
    
    @Override
    public boolean coinsOrDistrict() {
        return getGold() < 2 || hand.highValuedDistrict(getGold());
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
            int countBadCards=getHand().nbTooExpensivesDistricts(getGold());
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
    	/*getCity().forEach((District d) -> {
    		if(d.getNom().equals("Ecole de Magie")) {
    			//d.setColor("TODO"); // Le joueur choisit la couleur
    		}
    	});*/
		super.getCharacter().collectRentMoney();
    }
    /**
         * Fonction pour récupérer le Role permettant 
         * d'avoir le plus d'argent
         * On utilisera hidden que si le joueur est le 
         * dernier à choisir son role ie nextPlayer.alreadyChosenRole==true
         */
    public Role roleToOptimizeCoins(ArrayList<Role> lefts,Role hidden){
        
        if(city.getSizeOfCity()==0){
            //une autre 
            return null;
        }
        else{
            ArrayList<String> availableColors=new ArrayList<>();
            lefts.stream().map(d -> d.getColor()).forEach(s->{
                if(s.equals("soldatesque") || s.equals("commerce") || s.equals("regligion") || s.equals("noblesse") ){
                    availableColors.add(s);
                }
            });
            String bestColor=this.city.mostPotentiallyPayingColor(availableColors);
            
            for(Role r:lefts){
                if(r.getColor().equals(bestColor)){
                    return r;
                }
            }
            return null;
        }
        
        
    }

    Role pickTargetRole(){
        Role character = this.getCharacter();
        ArrayList<Role> roles = this.getBoard().getRoles();
        Role target;
        switch(character.getPosition()){
            case 1:
                target = roles.get(6);
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
}