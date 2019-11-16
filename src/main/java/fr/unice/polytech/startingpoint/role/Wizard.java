package fr.unice.polytech.startingpoint.role;

import fr.unice.polytech.startingpoint.board.District;

import java.util.ArrayList;

public class Wizard extends Role{

    public Wizard(){
        super(3);
    }

    private void action(){
        this.player.exchangeHands();
    }

    @Override
    public void useSpecialPower() {
        if(this.player.getTargetToExchangeHandWith()==null){
            ArrayList<District> hand = new ArrayList<District>(this.player.getHand());
            hand.removeAll(this.player.getCardsToExchange());
            ArrayList<District> tmphand = new ArrayList<District>(player.getBoard().exchangeMany(this.player.getCardsToExchange()));
            hand.addAll(tmphand);
            this.player.setHand(hand);
            System.out.println(this+" (joueur numero "+this.player.getId()+") echange des cartes avec le deck");
        }
        else{
            this.action();
            System.out.println(this+" (joueur numero "+this.player.getId()+") echange ses cartes avec le joueur numero "+this.player.getTargetToExchangeHandWith().getId());
        }
        

    }

    
}
