package fr.unice.polytech.startingpoint.role;

import java.util.List;

import fr.unice.polytech.startingpoint.board.District;
import fr.unice.polytech.startingpoint.player.Hand;

public class Wizard extends Role{

    public Wizard(){
        super(3);
    }
    //TODO revoir 
    private void action(){
        this.player.exchangeHands();
    }

    
    @Override
    public void useSpecialPower() {
        if(this.player.getTargetToExchangeHandWith()==null){
            //contenu de la main sans les cartes à échanger
            List<District> hand =this.player.getHand().contentExcept(this.player.getCardsToExchange());
            
            hand.addAll(player.getDeck().exchangeMany(this.player.getCardsToExchange()));

            this.player.setHand(new Hand(hand));
            System.out.println(this+" (joueur numero "+this.player.getId()+") echange des cartes avec le deck");
        }
        else{
            this.action();
            System.out.println(this+" (joueur numero "+this.player.getId()+") echange ses cartes avec le joueur numero "+this.player.getTargetToExchangeHandWith().getId());
        }
        

    }

    
}
