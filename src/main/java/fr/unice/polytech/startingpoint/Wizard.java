package fr.unice.polytech.startingpoint;

public class Wizard extends Role{

    Wizard(){
        super(3);
    }

    void action(Player player){
        //this.player.exchangeHands(player);
    }

    @Override
    void useSpecialPower() {
        if(this.player.getTargetToExchangeHandWith()==null){
            //Echanger avec le Deck Assets.TheDeck
        }
        else{
            this.action(this.player.getTargetToExchangeHandWith());
        }
        

    }

    
}
