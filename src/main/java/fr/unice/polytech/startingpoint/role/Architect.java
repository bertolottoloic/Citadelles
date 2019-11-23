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
        if(this.player.getDeck().numberOfCards()>=2){
            this.player.getHand().addAll(this.player.getDeck().withdrawMany(2));
        }
    }

    @Override
    public void useSpecialPower() {
        this.action();
    }
}
