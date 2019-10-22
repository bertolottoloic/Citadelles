package fr.unice.polytech.startingpoint;

public class Architect extends Role {

    Architect(){
        super(7);
        this.numberDistrictBuildable+=2;
        
    }

    /**
        * Le pouvoir spécial de l'architecte c'est de pouvoir prendre 
        *deux cartes en plus
        */
    void action(){
        if(Assets.TheDeck.lenght()>=2){
            this.player.getHand().addAll(Assets.TheDeck.withdrawMany(2));
        }
    }

    @Override
    void useSpecialPower() {
        this.action();
    }
}
