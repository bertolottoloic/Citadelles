package fr.unice.polytech.startingpoint;

public class Wizard extends Role{

    Wizard(){
        super(3);
    }

    void action(){
        this.player.exchangeHands();
    }

    @Override
    void useSpecialPower() {
        if(this.player.getTargetToExchangeHandWith()==null){
          this.player.setHand(Assets.TheDeck.exchangeMany(this.player.getHand()));
        }
        else{
            this.action();
        }
        

    }

    
}
