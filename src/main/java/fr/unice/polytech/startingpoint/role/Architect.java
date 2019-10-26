package fr.unice.polytech.startingpoint.role;

public class Architect extends Role {

    public Architect(){
        super(7);
        this.numberDistrictBuildable+=2;
        
    }

    /**
        * Le pouvoir spécial de l'architecte c'est de pouvoir prendre 
        *deux cartes en plus
        */
    private void action(){
        if(player.getBoard().numberOfCardsOfDeck()>=2){
            this.player.getHand().addAll(player.getBoard().withdrawMany(2));
        }
    }

    @Override
    public void useSpecialPower() {
        this.action();
    }
}
